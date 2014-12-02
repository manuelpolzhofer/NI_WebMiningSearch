
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NI WebMining</title>
</head>
<body>
<h1>Search Engine with live crawling</h1>
This Search Engine will first crawl the webpages and search afterwards.
<br>
results in real time!
<br>
Hint: only enter high "stop after" numbers if you have some time ;)

<br>
<br>
<h3>Search:</h3>


 <form name="search" action="SearchServlet" method="post">
        <p>
            Crawler Start: <input name="startpage" value = "http://en.wikipedia.org/wiki/Data_mining" size="44"/> 
        </p>
        
        <p> Stop after: <input name="maxresults" size="4" value="10"/> </p> 
        
        <p>
            Search for: <input name="query" value ="data" size="44"/> 
        </p>
            

       
                <p> <input type="radio" name="ranking" value="boolean" checked="checked" />Boolean Retrieval<br> </p>
          
        <p> <input type="radio" name="ranking" value="vsr"/>Vector Space retrieval<br> </p>
         <p> <input type="radio" name="ranking" value="vsr_html"/>Vector Space retrieval + HTML Markup<br> </p>
         <p> <input type="radio" name="ranking" value="page_rank"/>Page Rank Algorithm<br> </p>     
       	<p> <input type="submit" value="Search"/> </p>

       
        
 </form>
</body>
</html>