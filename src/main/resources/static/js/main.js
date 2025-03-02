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
			let trendRankColor = "";
			let trendRankArrow = "";

			let nextArr = null;
			if (i < (recLength - 1)) {
				nextArr = trendArray[i + 1];
			}
			if (nextArr != null && v[2] != nextArr[2]) {
				trendColor = (v[2] < nextArr[2]) ? "text-danger" : "text-success";
				trendArrow = (v[2] < nextArr[2]) ? "fas fa-caret-down me-1" : "fas fa-caret-up me-1";
			}
			if (nextArr != null && v[4] != nextArr[4]) {
				trendRankColor = (v[4] > nextArr[4]) ? "text-danger" : "text-success";
				trendRankArrow = (v[4] > nextArr[4]) ? "fas fa-caret-down me-1" : "fas fa-caret-up me-1";
			}

			stockHist += `<tr>
							<td><p class="fw-bold mb-1"> ${v[0]}</p></td>
							<td><span class="${trendRankColor}">
                  				<i class="${trendRankArrow}"></i><span>${v[4]}</span>
                			</span></td>
							<td><span class="${trendColor}">
                  				<i class="${trendArrow}"></i><span>${v[2]} %</span>
                			</span></td>
					</tr>`;
		});
		$("#stockTrendHistTable tbody").html(stockHist);
		$('#trendTableStock').text(stockName);
		$('#myTrendTableModal').modal('show');

	});
	$('#equitySmallCapCard').on('click', 'div', function() {
		$('.fundTypeCard').css("border-style", "")
		loadTrendDataTable('EQUITY_SMALL_CAP', '#equitySmallCapCard');
	});
	$('#equityMidCapCard').on('click', 'div', function() {
		$('.fundTypeCard').css("border-style", "")
		loadTrendDataTable('EQUITY_MID_CAP', '#equityMidCapCard');
	});
	$('#equityLargeCapCard').on('click', 'div', function() {
		$('.fundTypeCard').css("border-style", "")
		loadTrendDataTable('EQUITY_LARGE_CAP', '#equityLargeCapCard');
	});

	$('#csvDownloadLink').on('click', 'i', function() {
		downloadCsvFile();
	});

	loadTrendDataTable('EQUITY_LARGE_CAP', '#equityLargeCapCard');
});
var stockTrendData = {};
var stockDataList = [];
var csvHeaderKeys = ["Stock Name", "Sector", "Rank", "AvgHolding", "FundHouse"];
var csvFileName="stock";
function downloadCsvFile() {
	const csvContent = "data:text/csv;charset=utf-8," + stockDataList.map(row => row.join(",")).join("\n");
	const encodedUri = encodeURI(csvContent);

	const link = document.createElement("a");
	link.setAttribute("href", encodedUri);
	link.setAttribute("download", csvFileName+".csv");
	document.body.appendChild(link);
	link.click();
}
function loadTrendDataTable(ruleType, fundTypeCardId) {
	stockDataList = [];
	csvFileName = ruleType;
	$(fundTypeCardId).css("border-style", "solid")
	$.ajax({
		url: "/listMfStockTrendRecords/" + ruleType, success: function(data) {
			let stockList = ``;
			if (data.length == 0) {
				stockList = `<tr><td colspan=5><div style="font-size:16px">No Data</div></td></tr>`;
			} else {
				$("#stockTrendTable tbody").html("");
				stockDataList.push(csvHeaderKeys);
				data.forEach(function(v, i) {
					stockTrendData[v.reportId] = v.holdingTrend;

					let trendArray = v.holdingTrend;
					let latestData = trendArray[trendArray.length - 1]

					let trendColor = "";
					let trendArrow = "";
					if (v.day2 > 0 && v.day1 != v.day2) {
						trendColor = (v.day1 < v.day2) ? "text-danger" : "text-success";
						trendArrow = (v.day1 < v.day2) ? "fas fa-caret-down me-1" : "fas fa-caret-up me-1";
					}

					let trendRankColor = "";
					let trendRankArrow = "";
					let prevRank = 0;
					if (trendArray.length > 1) {
						let prevData = trendArray[trendArray.length - 2];
						prevRank = prevData[4];
					}
					if (prevRank > 0 && v.ruleRank != prevRank) {
						trendRankColor = (v.ruleRank > prevRank) ? "text-danger" : "text-success";
						trendRankArrow = (v.ruleRank > prevRank) ? "fas fa-caret-down me-1" : "fas fa-caret-up me-1";
					}

					stockList += `<tr id="${v.reportId}" style="cursor: pointer;">
							<td><p class="fw-bold mb-1"> ${v.stockName}</p></td>
							<td><p class="fw-normal mb-1"> ${v.sector} </p></td>
							<td><span class="${trendRankColor}">
                  				<i class="${trendRankArrow}"></i><span>${v.ruleRank}</span>
                			</span></td>
							<td><span class="${trendColor}">
                  				<i class="${trendArrow}"></i><span>${v.day1} %</span>
                			</span></td>
							<td><p class="fw-normal mb-1"> ${latestData[1]} </p></td>
							</tr>`;
					let csvRow = [];
					csvRow.push(v.stockName);
					csvRow.push(v.sector);
					csvRow.push(v.ruleRank);
					csvRow.push(v.day1);
					csvRow.push(latestData[1]);
					stockDataList.push(csvRow);
				});
				$("#stockTrendTable tbody").html(stockList);
			}
		}
	});
}


