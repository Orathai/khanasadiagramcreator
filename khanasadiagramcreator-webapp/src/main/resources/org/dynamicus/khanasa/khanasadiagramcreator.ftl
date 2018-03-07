<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <title>Khanasa diagram Creator</title>
    </head>
    <body>
        <div align="center">
            <h3>${message}</h3>

            <form name="submit" action="/submit.html" method="post">
                <table>
                    <tr>
                        <td align="right">Database Type :</td>
                        <td>
                            <select name="dbType" >
                                <option selected="selected" value=""> - Database type - </option>
                                <#list databaseTypeMap?keys as dbType>
                                    <option value=${dbType}>${databaseTypeMap[dbType]}</option>
                                </#list>
                            </select> <span class="txt_red">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">IP Address :</td>
                        <td>
                            <input type="text" name="ip"><span class="txt_red">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">Port :</td>
                        <td><input type="text" name="port"></td>
                    </tr>
                    <tr>
                        <td align="right">Database Name :</td>
                        <td>
                            <input type="text" name="database"><span class="txt_red">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">Database User :</td>
                        <td>
                            <input type="text" name="user"><span class="txt_red">*</span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">Password :</td>
                        <td>
                            <input type="password" name="password"><span class="txt_red">*</span>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="submit">
            </form>
        </div>

    </body>

</html>