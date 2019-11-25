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
        <div>
            <h2>please enter your new value</h2>
             
        </div>
    </div>
	<div>
    <table>
        </table>
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