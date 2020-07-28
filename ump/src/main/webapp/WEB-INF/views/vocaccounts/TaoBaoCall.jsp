<%
response.setContentType("text/html;charset=utf-8");
System.out.println("+++++++++++++++++++++++");
String address = String.valueOf(request.getAttribute("address"));
String cleintId= String.valueOf(request.getAttribute("client_id"));
String responseType = String.valueOf(request.getAttribute("response_type"));
String redirect_uri= String.valueOf(request.getAttribute("redirect_uri"));
System.out.println(address+"?client_id="+cleintId+"&response_type="+responseType+"&redirect_uri="+redirect_uri);
response.sendRedirect(address+"?client_id="+cleintId+"&response_type="+responseType+"&redirect_uri="+redirect_uri);
//response.sendRedirect("http://www.hao123.com/");
//request.getRequestDispatcher("http://www.baidu.com/").forward(request, response);

System.out.println("+++++++++++++++++++++++");
%>
