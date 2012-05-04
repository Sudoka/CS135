<%@page import="support.*, java.util.*, gradstudent.*" %>
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css">    
</head>
<body>
      <div class="container" style="margin-top:20px">
    <div class="row-fluid">
        <div class="span12">
  <% 
  // grab old info
  GradStudent student = (GradStudent) application.getAttribute("student");

student.setSpecialization(request.getParameter("speciailization"));

// record new info
  application.setAttribute("student", student);
  %>

<%-- display old info --%>
<h1>Welcome <%= student.getFirstName() %> <%= student.getLastName() %>!<br></h1>
citizenship: <%= student.getCitizenship() %><br>
residence: <%= student.getResidence() %><br>
address: <%= student.getAddress().getStreetNumber() %> <%= student.getAddress().getStreetName() %> <Br>
<%= student.getAddress().getCity() %>, <%= student.getAddress().getStateCode() %> <%= student.getAddress().getZipcode() %><br>
(<%= student.getAddress().getAreaCode() %>) <%= student.getAddress().getPhoneNumber() %><br><br>


<%-- display all degree info --%>
<% 
for(int i=0; i<student.getDegrees().size(); i++) {
%>
  Degree # <%= i %> <br>
  Degree Location: <%= student.getDegrees().get(i).getLoc() %><Br>
  University: <%= student.getDegrees().get(i).getUni() %><br><br>
<% 
}
%>


<button> Submit Application</button> <Button>Cancel</button>
  </form>
  </div>
</div>
</div>
  </body>
  </html>