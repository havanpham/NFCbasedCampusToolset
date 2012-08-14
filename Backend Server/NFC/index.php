<?php
$connect =mysql_pconnect('localhost','root','')or die("ko ket noi dc");
$connect =mysql_select_db('tv2')or die("ko co cai nay");
mysql_query("SETNAME 'UTF8'");



?>
<html>
<head>
<title>Test sigate</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="style.css" type="text/css" media="screen"/>
<script src="jquery.js" type="text/javascript"></script>
<script src="dimensions.js" type="text/javascript"></script>
<script src="autocomplete.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function(){
	    setAutoComplete("searchField", "results", "autocomplete.php?part=");
	    setAutoComplete("","","asd.php?");
	});

</script>

</head>

<body>


<form name="tim" method="POST" >
	<div style="position: absolute; left: 10; top:160;" id="map">
			<img alt="" src="thuvien4.png" width="920px" height="600px"/>
            </div>
	<div id="container" style="position:absolute; left: 10;top: 10; "  >

		<div id="main_menu">
			<a href="index.php" class="aactive">HOME</a> 
			<a href="index.php">LIBRARY MAP</a> 
			<a href="edit.php">EDIT</a> 
            
			<a href="test2.php">HELP</a>
		</div>

		<div id="search_form" >
			<table>
				<tr>
					<td>Select major:</td>
					<td><select name="abc"  >
                    <? 
                    
					$q1=mysql_query('SELECT * FROM major');

					while($rowa=mysql_fetch_array($q1))
				{
					echo '<option value="'.$rowa['name_m'].'">';
						echo $rowa['name_m'].'</option>';
					}

                    ?> 					
					</select> </td>
                
				
				
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
                <?php
                  require_once("index.php");

    if(isset($_POST['sb']))
    {
         $ten=$_POST['searchField'];
        $coso=$_POST['abc'];     
      
        $traloi7=mysql_query("SELECT * FROM books WHERE name like'{$ten}'") or die("Don't Have the book");

$ketqua7=mysql_fetch_array($traloi7);
;

//$ketqua8=mysql_fetch_array($traloi8);
//$toadoy= $ketqua7['toadoy'];
//$toadox= $ketqua7['toadox'];
$khoa=$ketqua7['major'];

if($ten=="")
{$traloi8=mysql_query("SELECT * FROM major WHERE name_m ='{$coso}'") or die("Don't find");
$tg=0;
}
else
{$traloi8=mysql_query("SELECT * FROM major WHERE id_m ='{$khoa}'") or die("Don't find");
$tg=1;
}
//$toadoy1= $ketqua8[''];
//$toadox1= $ketqua8['toadox'];


echo "<br />";






$toadox1=array();
$toadoy1=array();
$i=0;
  while( $ketqua8=mysql_fetch_array($traloi8,MYSQL_ASSOC))
   {

   $toadox1[$i]=$ketqua8['px_x'];
  
     $toadoy1[$i]=$ketqua8['px_y'];
    $tenkhoa=$ketqua8['name_m'];
      $i++;
    
    
}
if(($khoa=="")&&($tg==1) )
    {echo ("Not in the library");
    $tg=10;
    }


if($tg==0)
{
   
    $trunggian=0;
}
if($tg==1)
{
    echo ("You have seached  :");

    echo $ten;
    
      echo "<br />";
      echo ("in ");
    $trunggian=1;
}





if($tg!=10)
{
    echo (" Major :");
    echo $tenkhoa;
    $trunggian1=1;
}

 $j=0;
 if($tg==1)
 {
    
    for($j=0;$j<$i;$j++) {
        ?>
 
 </div>
<div style="position:absolute;left:<?php print($toadox1[$j]-12)?>;top:<?php print($toadoy1[$j]-33)?>"id="layer10">
<img src="abc.gif" onmouseover="check2()" />

</div>
 </div>
 <?php }} ?>
 
 
 
  <?php 

    if($tg==0)
{
    for($j=0;$j<$i;$j++) {
  
 ?>
 
 </div>
<div style="position:absolute;left:<?php print($toadox1[$j]-12)?>;top:<?php print($toadoy1[$j]-33)?>"id="layer11">
<img src="abc.gif" />

</div>
 </div>


 
<?php   } } ;
    
    
    
    
    
    
    
    } 
                ?>
		</div>

		
	</div>
    <?php 
    $sql=mysql_query("SELECT * FROM MAJOR");
    while($ketqua9=mysql_fetch_array($sql,MYSQL_ASSOC))
    {
        
    
    ?>
    <div style="position: absolute; color: black; font-weight: bold;font-size: 12; font-style: italic; left:<?php print($ketqua9['px_x']-50)?>;top:<?php print($ketqua9['px_y']-12)?>" id="nhan">
        <?php 
       




if(strlen($ketqua9['name_m'])<=16)
echo $ketqua9['name_m'];
else
{
    
   ?>
   <div style="position: absolute;"id="nhan2">
    <?php
    echo $ketqua9['name_m'];
    ?>
    </div>
    <?php
}
        
        
       
        ?>
        
    </div>
    <?php }
    ?>
    
</form>
</body>

</html>
