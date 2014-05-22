$(function() {
	$('textarea.taMensagem').keyup(function() {
	    var $textarea = $(this);
	    var max = 2000;
	    if ($textarea.val().length > max) {
	        $textarea.val($textarea.val().substr(0, max));
	    }
	});
	
	$('li').click(function() {
		$('li').removeClass('active');
		$('li.' + this.className).addClass('active');
	});
});

//@ source=funcoes.js