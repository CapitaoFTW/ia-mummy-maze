<HTML>

<SCRIPT LANGUAGE="JavaScript">

function SubmitData()
{	
	document.DataForm.submit();
}

setTimeout("SubmitData();", 1000);

</SCRIPT>

Submitting Survey Results ...

<FORM NAME="DataForm" ACTION="http://www.popcap.com/submitsurvey.php" METHOD=POST>
<INPUT TYPE="HIDDEN" NAME="prod" VALUE="MummyMaze">
<INPUT TYPE="HIDDEN" NAME="data" VALUE="$DATA">
</FORM>

</BODY>
</HTML>