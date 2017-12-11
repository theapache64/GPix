<%@ page import="com.theah64.gpix.server.primary.database.tables.Users" %>
<%@ page import="com.theah64.gpix.server.primary.models.User" %>
<%@ page import="com.theah64.gpix.server.primary.utils.*" %><%--
  Created by IntelliJ IDEA.
  User: theapache64
  Date: 11/12/17
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Get API Key - GPix</title>
    <%@include file="common_headers.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <h2>Generate API Key</h2>
            <br>
            <form action="index.jsp" method="POST">
                <div class="form-group">
                    <label for="iEmail">Email:</label>
                    <input id="iEmail" type="text" class="form-control" name="<%=Users.COLUMN_EMAIL%>"/>
                </div>
                <%
                    final Form form = new Form(request, new String[]{Users.COLUMN_EMAIL});

                    try {
                        if (form.isSubmitted() && form.isAllRequiredParamsAvailable()) {

                            final String email = form.getString(Users.COLUMN_EMAIL);

                            if (CommonUtils.emailPattern.matcher(email).matches()) {

                                //Checking if the email already has an api_key.
                                User user = Users.getInstance().get(Users.COLUMN_EMAIL, email);
                                final boolean isAdded;

                                if (user != null) {
                                    isAdded = true;
                                } else {
                                    final String apiKey = RandomString.getNewApiKey(CommonUtils.API_KEY_LENGTH);
                                    user = new User(null, email, apiKey, null, 0, true);
                                    isAdded = Users.getInstance().add(user);
                                }

                                if (isAdded) {

                                    final String message = String.format("Hey, New user established\n\nUser: %s\n\nThat's it. :) ", user.toString());

                                    final User finalUser = user;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {

                                            MailHelper.sendApiKey(finalUser.getEmail(), finalUser.getApiKey());

                                            //Sending mail to admin about the new member join
                                            MailHelper.sendMail("theapache64@gmail.com", "GPix - New user joined", message);
                                        }
                                    }).start();

                                    StatusResponse.redirect(response, "Success", "API Key sent to " + email + ". Please check your inbox");


                                } else {
                                    throw new Request.RequestException("Failed to add new user");
                                }


                            } else {
                                throw new Request.RequestException("Invalid email");
                            }

                        }
                    } catch (Request.RequestException e) {
                        e.printStackTrace();
                %>
                <p class="text-danger"><%=e.getMessage()%>
                </p>
                <%
                    }
                %>

                <input name="<%=Form.KEY_IS_SUBMITTED%>" class="btn btn-primary pull-right" type="submit"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
