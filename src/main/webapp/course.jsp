<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Student Management</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link
			rel="stylesheet"
			href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
		/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

		<style>
		</style>
		<script>
			
		</script>
	</head>

	<body>
		<nav class="navbar navbar-inverse" style="border-radius: 0px">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="student">Student Management</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="student">Student</a></li>
					<li class="active"><a href="course">Course</a></li>
					<li><a href="score">Score</a></li>
				</ul>
			</div>
		</nav>
		<div class="container-fluid">
			<h1>Course List</h1>
		</div>
	</body>
</html>