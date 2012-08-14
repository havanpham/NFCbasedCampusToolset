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
<script language="javascript">
var i=0;
 function showit() {
    i=1;

    }

    function showitMOZ(e) {
            
            
        if(i==0){
         window.captureEvents(Event.CLICK);
      document.onmousemove=showitMOZ;
      
    
        
    document.forms['edit'].xcoord.value=e.pageX;

    document.forms['edit'].ycoord.value=e.pageY;
    window.onclick=showit;
    }}
   
  
    </script>
<form name="edit" method="POST" >
<div style="position: absolute; left: 10; top:160;" id="map">
			<img alt="" src="thuvien4.png" width="920px" height="600px"/>
            </div>
	<div id="container" style="position:absolute; left: 10;top: 10; "  >

		<div id="main_menu">
			<a href="index.php" >HOME</a> 
			<a href="index.php">LIBRARY MAP</a> 
			<a href="edit.php" class="aactive">EDIT</a> 
            
			<a href="test2.php">HELP</a>
		</div>

		<div id="search_form" >
			<table>
                <tr>
					<td><input  type="radio" name="abc" value="major" checked=""/>Major</td>
					<td><input  type="radio" name="abc" value="books"/>Book</td>
						
				</tr>
				<tr>
					<td><input type="submit" value="Insert"  name="insert" /></td>
				
                    <td><input type="submit" value="Delete" name="delete" /></td>				
				</tr>
                <tr>
                        <td> <?php $kiemtra="";
                        
                                    if(isset($_POST['insert']))
                                    {
                                        $kiemtra=$_POST['abc'];
                                        if($kiemtra=='major')
                                        {
                                            
                        ?>
                                    Major: <input type="text" name="major" /> ID:<input type="text" name="id" size="2"/>
                                    <input type="button" value="Location" onclick="showitMOZ()" />x = <input name="xcoord" type="text" readonly size="1"/> 
y = <input name="ycoord" type="text" readonly size="1"/>
                                     <input type="submit" name="ok" value="OK" />
                                    <?php }
                                        if($kiemtra=='books')
                                        {
                                     ?>
                                     id:<input type="text" name="idbook" size="6" /> Name:<input type="text" name="book" /> 
                                     <select name="abcd"  >
                    <? 
                    
					$q1=mysql_query('SELECT * FROM major');

					while($rowa=mysql_fetch_array($q1))
				{
					echo '<option value="'.$rowa['id_m'].'">';
						echo $rowa['name_m'].'</option>';
					}

                    ?>
                      </select><input type="submit" name="ok1" value="OK" />    
                    <?php }
                    ?>
                  
                   
                    </td>
                        <td>
                        <?php
                    }
                     if(isset($_POST['delete']))
                       {
                                $kiemtra=$_POST['abc'];
                                if($kiemtra=='major')
                                        {
                                            
                        ?>
                        <select name="abcde"  >
                    <? 
                    
					$q1=mysql_query('SELECT * FROM major');

					while($rowa=mysql_fetch_array($q1))
				{
					echo '<option value="'.$rowa['id_m'].'">';
						echo $rowa['name_m'].'</option>';
					}

                    ?>
                    </select><input type="submit" value="OK" name="ok2" />
                    <?php
                    }
                        if($kiemtra=='books')
                        {
                    ?> 
                        <select name="abcdef"  >
                    <? 
                    
					$q1=mysql_query('SELECT * FROM books');

					while($rowa=mysql_fetch_array($q1))
				{
					echo '<option value="'.$rowa['name'].'">';
						echo $rowa['name'].'</option>';
					}

                    ?>
                    </select><input type="submit" value="OK" name="ok3" />
                    
                    <?php
                    }
                    }
                    ?>
                  
                    <?php
                        
                            
                             
                        if(isset($_POST['ok']))
                        {  
                            
                              
                            
                                     $text1=$_POST['id'];
                                      $text2=$_POST['major'];
                                      $text6=$_POST['xcoord'];
                                      $text7=$_POST['ycoord'];
                                     
                            
                                    mysql_query("INSERT INTO major VALUES ('{$text1}','{$text2}','{$text6}','{$text7}')") or die("ERROR");
                                echo("Done!");
                                $kiemtra="";
                            
                              
                                
                        }
                        if(isset($_POST['ok1']))
                        {  
                            
                              
                            
                                      $text3=$_POST['idbook'];
                                      $text4=$_POST['book'];
                                      $text5=$_POST['abcd'];
                            
                                    
                                    mysql_query("INSERT INTO books VALUES ('{$text3}','{$text4}','{$text5}')") or die ("ERROR");
                                    echo("Done!!");
                                       $kiemtra="";
                                
                        }
                        if(isset($_POST['ok2']))
                        {  
                            
                              
                            
                                    
                                      $text6=$_POST['abcde'];
                            
                                    
                                    mysql_query("DELETE FROM major WHERE id_m='{$text6}'")or die("ERROR");
                                    echo("Delete !!");
                                       $kiemtra="";
                                
                        }
                          if(isset($_POST['ok3']))
                        {  
                            
                              
                            
                                    
                                      $text7=$_POST['abcdef'];
                            
                                    
                                    mysql_query("DELETE FROM books WHERE name='{$text7}'")or die("ERROR");
                                    echo("Delete!");
                                       $kiemtra="";
                                       
                                
                        }
                    
                      
                    ?>
                    					

                                    
                                        
                        
                </tr>
				
			</table>
			
            
            
                
		</div>
 
                

		
	</div>
    </dir>
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
   <div style="position: absolute; font-size: 12;"id="nhan2">
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
