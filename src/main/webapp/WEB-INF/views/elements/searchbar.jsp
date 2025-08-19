<%
  String styleVariant = (String) request.getAttribute("styleVariant");
  String searchIcon = "";
  System.out.println(styleVariant+ styleVariant);
  if (styleVariant == null) {

  } else {
    styleVariant = "swap__input";
    searchIcon = "swap__searchIcon";
  }
%>
<div class="spectacledcoder-search-bar" id="searchbar">

  <input id="searchbarinput" class="spectacledcoder-search-bar-input <%= styleVariant %>" type="text" name="search"
         id="searchval"
         placeholder="BTC USDT"/>
  <img id="searchicon" width="22" height="22" src="https://img.icons8.com/sf-black/100/ffffff/search.png" class="<%= searchIcon %>" alt="search"/>
  <div class="spectacledcoder-dropdown" id="dropdown">
    <ul id="resultlist">
    </ul>
  </div>
</div>
