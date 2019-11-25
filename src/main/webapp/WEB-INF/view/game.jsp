<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<body>
    <div>
        <div>
            <h1>game of three started </h1>
            <h2> please choose manual or automatic input</h2>
            <div>
                inital number ${initalValue}
            </div>
			<form method="POST" action="startPlaying">
				  <select name="choice">
					<option value="manual">manual</option>
					<option value="automatic">automatic</option>
				  </select>
				  <input type="hidden" id="initalValue" name="initalValue" value= "${initalValue}"/>
				  <input type="submit" value="Submit">
			</form>
        </div>
    </div>
</body>
</html>