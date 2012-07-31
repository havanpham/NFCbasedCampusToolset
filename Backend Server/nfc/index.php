<html>
<head>
<title>Test sigate</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="style.css" type="text/css" media="screen">
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">
	function lookup(inputString) {
		if(inputString.length == 0) {
			// Hide the suggestion box.
			$('#suggestions').hide();
		} else {
			$.post("rpc.php", {queryString: ""+inputString+""}, function(data){
				if(data.length >0) {
					$('#suggestions').show();
					$('#autoSuggestionsList').html(data);
				}
			});
		}
	} // lookup
	
	function fill(thisValue) {
		$('#inputString').val(thisValue);
		setTimeout("$('#suggestions').hide();", 200);
	}
</script>
</head>

<body>
	<div id="container">

		<div id="main_menu">
			<a href="index.php" class="aactive">Trang chủ</a> 
			<a href="index.php">Bản đồ thư viện</a> 
			<a href="search.php">Tìm kiếm</a> 
			<a href="test2.php">Liên hệ</a>
		</div>

		<div id="search_form">
			<table>
				<tr>
					<td>Select major:</td>
					<td><select name="book" width="30px">
					<?php
					$connect=mysql_connect('localhost','root','')or die ('Cannot connect database');
					mysql_select_db('lib_nfc',$connect);

					$q1=mysql_query('SELECT * FROM major');

					while($rowa=mysql_fetch_array($q1))
					{
						echo '<option value="'.$rowa['id_m'].'">';
						echo $rowa['name_m'].'</option>';
					}

					?>
					</select></td>
				</tr>
				
				<tr>
					<td>Search (book name):</td>
					<td>
						<p id="auto">
							<input id="searchField" name="searchField" type="text" />
						</p></td>
				</tr>
			</table>
			<input id="search_book" type="submit" name="sb" value="Search" />
		</div>

		<div id="map">
			<img alt="" src="map_lib.png" width=900px height="600px">
		</div>
	</div>

</body>

</html>
