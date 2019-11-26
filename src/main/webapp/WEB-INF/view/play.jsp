<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<body>
    <div>
        <div>
            <h1>your turn</h1>
			 <div>
               current value ${value}
            </div>
			<form method="POST" action="play">
				
				<div>
				<table>
					<% 
						String choice = (String)session.getAttribute("choice");
						if ("MANUAL".equals(choice))
						{ 
					%>
					<div>
						<div>
							<h2>please enter your new value you want to add {-1,0,1} to make current value divisble by 3</h2>
							<h2>${message}</h2>
						</div>
					</div>
					<tr>
						<td><input type="text" id="value" name="value"/></td>
					</tr>

					<% } else{
					%>
						<h2>${message}</h2>
					<% } 
					%>
					<tr>
						<td colspan="2"><input type="submit"></td>
					</tr>
				</table>
				</div>
			</form>
        </div>
    </div>
</body>
</html>