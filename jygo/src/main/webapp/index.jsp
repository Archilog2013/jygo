<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="JAVASCRIPT">
            onClipEvent (enterFrame) {
            if (_xmouse > 250) {
            this._x+=(_xmouse-250)/30
            }
            if (_xmouse < 250) {
            this._x+=(_xmouse)/30
            }
        </script>
    </head>
    <body style="background-image:url(The_Teek.jpg);">
        <h1>Jeu de GOOOOODDD</h1>
        
        <form id="jygo" action="/jygo/webresources/go/" method="GET">
            <input type="text" id="cmd" name="cmd"/>
            <input type="submit"/>
        </form>
    </body>
</html>
