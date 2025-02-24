//https://select2.org/configuration
var mfselect2Data = [];
function matchCustom(params, data) {
	// If there are no search terms, return all of the data
	if ($.trim(params.term) === '') {
		return null;
	}

	if (params.term.length < 3) {
		return null;
	}

	// Do not display the item if there is no 'text' property
	if (typeof data.text === 'undefined') {
		return null;
	}

	// `params.term` should be the term that is used for searching
	// `data.text` is the text that is displayed for the data object
	if (data.text.indexOf(params.term) > -1) {
		var modifiedData = $.extend({}, data, true);
		// You can return modified objects from here
		// This includes matching the `children` how you want in nested data sets
		return modifiedData;
	}

	// Return `null` if the term should not be displayed
	return null;
}
$(function() {
	$.each(mfdata.instruments, function(index, val) {
		//console.log(index, val[0]);
		let mflistitem = {};
		mflistitem['id'] = val[0]
		mflistitem['text'] = val[2] + " (" + val[9] + ", " + val[10] + ", " + val[11] + ") - [" + val[0] + "]";
		mfselect2Data.push(mflistitem);
	});
	$("#mfselect2").select2({
		theme: "classic",
		placeholder: "Search for fund..",
		minimumInputLength: 3,
		//minimumResultsForSearch: 10,
		//closeOnSelect: false,
		//matcher: matchCustom,
		data: mfselect2Data
	});
	$('#mfselect2').on('select2:select', function(e) {
		var data = e.params.data;
		//alert(data.text); selectedMfgroup
		$("#selectedMfgroup").append('<label class="list-group-item"><input class="form-check-input me-1" value="' + data.id + '" type="checkbox" checked>' + data.text + '	</label> ');
		$('#mfselect2').val(null).trigger('change');

	});

	$('#stockTrendTable').on('click', 'tr', function() {
		let trendArray = stockTrendData[this.id];
		let recLength = trendArray.length;

		let stockHist = ``;
		let stockName = this.children[0].innerText;
		trendArray.reverse().forEach(function(v, i) {
			let trendColor = "";
			let trendArrow = "";
			let nextArr = null;
			if (i < (recLength-1)) {
				nextArr = trendArray[i + 1];
			}
			if (nextArr != null && v[2] != nextArr[2]) {
				trendColor = (v[2] < nextArr[2]) ? "text-danger" : "text-success";
				trendArrow = (v[2] < nextArr[2]) ? "fas fa-caret-down me-1" : "fas fa-caret-up me-1";
			}
			stockHist += `<tr>
							<td><p class="fw-bold mb-1"> ${v[0]}</p></td>
							<td><span class="${trendColor}">
                  				<i class="${trendArrow}"></i><span>${v[2]} %</span>
                			</span></td>
							<td><p class="fw-normal mb-1"> ${v[4]} </p></td>
					</tr>`;
		});
		$("#stockTrendHistTable tbody").html(stockHist);
		$('#trendTableStock').text(stockName);
		$('#myTrendTableModal').modal('show');

	});
	$('#equitySmallCapCard').on('click', 'div', function() {
		$('.fundTypeCard').css("border-style","")
		loadTrendDataTable(4952, '#equitySmallCapCard');
	});
	$('#equityMidCapCard').on('click', 'div', function() {
		$('.fundTypeCard').css("border-style","")
		loadTrendDataTable(9952, '#equityMidCapCard');	
	});
	$('#equityLargeCapCard').on('click', 'div', function() {
		$('.fundTypeCard').css("border-style","")
		loadTrendDataTable(14952, '#equityLargeCapCard');	
	});
	loadTrendDataTable(14952,'#equityLargeCapCard');	
});
var stockTrendData = {};
function loadTrendDataTable(ruleId, fundTypeCardId) {
	
	$(fundTypeCardId).css("border-style","solid")
	$.ajax({
		url: "/listMfStockTrendRecords/" + ruleId, success: function(data) {
			let stockList = ``;
			if (data.length == 0) {
				stockList = `<tr><td colspan=5><div style="font-size:16px">No Data</div></td></tr>`;
			} else {
				$("#stockTrendTable tbody").html("");
				data.forEach(function(v, i) {
					stockTrendData[v.reportId] = v.holdingTrend;

					let trendArray = v.holdingTrend;
					let latestData = trendArray[trendArray.length - 1]

					let trendColor = "";
					let trendArrow = "";
					if (v.day1 != v.day2) {
						trendColor = (v.day1 < v.day2) ? "text-danger" : "text-success";
						trendArrow = (v.day1 < v.day2) ? "fas fa-caret-down me-1" : "fas fa-caret-up me-1";
					}
					stockList += `<tr id="${v.reportId}" style="cursor: pointer;">
							<td><p class="fw-bold mb-1"> ${v.stockName}</p></td>
							<td><p class="fw-normal mb-1"> ${v.sector} </p></td>
							<td><span class="${trendColor}">
                  				<i class="${trendArrow}"></i><span>${v.day1} %</span>
                			</span></td>
							<td><p class="fw-normal mb-1"> ${latestData[1]} </p></td>
							</tr>`;
				});
				$("#stockTrendTable tbody").html(stockList);
			}
		}
	});
}


