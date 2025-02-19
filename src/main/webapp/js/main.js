//https://select2.org/configuration
var mfselect2Data =[];
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
$(function(){
	$.each(mfdata.instruments , function(index, val) { 
		//console.log(index, val[0]);
		let mflistitem = {};
		mflistitem['id'] = val[0]
		mflistitem['text'] = val[2]+" ("+val[9]+", "+val[10]+", "+val[11]+") - ["+val[0]+"]";
		mfselect2Data.push(mflistitem);
	});
    $("#mfselect2").select2({
		theme: "classic",
		placeholder: "Search for fund..",
		minimumInputLength: 3 ,
		//minimumResultsForSearch: 10,
		//closeOnSelect: false,
		//matcher: matchCustom,
		data: mfselect2Data
	});
	$('#mfselect2').on('select2:select', function (e) {
		var data = e.params.data;
		//alert(data.text); selectedMfgroup
		$("#selectedMfgroup").append('<label class="list-group-item"><input class="form-check-input me-1" value="'+data.id+'" type="checkbox" checked>'+data.text+'	</label> ');
	$('#mfselect2').val(null).trigger('change');
		
});
});
