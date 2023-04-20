<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ page
language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" %>

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
			.hide {
				display: none;
			}
			.badge:hover {
				color: #ffffff;
				text-decoration: none;
				cursor: pointer;
			}
			.badge-error {
				background-color: #b94a48;
			}
			.badge-error:hover {
				background-color: #953b39;
			}
			.badge-warning {
				background-color: #f89406;
			}
			.badge-warning:hover {
				background-color: #c67605;
			}
			.badge-success {
				background-color: #5cc45e;
			}
			.badge-success:hover {
				background-color: #356635;
			}
			.badge-info {
				background-color: #3a87ad;
			}
			.badge-info:hover {
				background-color: #2d6987;
			}
			.badge-inverse {
				background-color: #333333;
			}
			.badge-inverse:hover {
				background-color: #1a1a1a;
			}
		</style>
		<script></script>
	</head>

	<body>
		<nav class="navbar navbar-inverse" style="border-radius: 0px">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="../">Student Management</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="../student">Student</a></li>
					<li><a href="../course">Course</a></li>
				</ul>
			</div>
		</nav>
		<div class="container-fluid">
			<h1>Student Information</h1>
			<div class="card col-md-12">
				<p class="card-title">
					<strong>Name: </strong
					><span class="badge badge-info">${STUDENT.name}</span>
				</p>
				<p class="card-title">
					<strong>Grade: </strong
					><span class="badge badge-warning">${STUDENT.grade}</span>
				</p>
				<p class="card-title">
					<strong>Birthday: </strong
					><span class="badge badge-success">${STUDENT.birthday}</span>
				</p>
				<p class="card-title"><strong>Notes: </strong>${STUDENT.notes}</p>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Name</th>
						<th scope="col">Score</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tempScore" items="${SCORES}" varStatus="loop">
						<tr>
							<th scope="row">${loop.index + 1}</th>
							<td>${tempScore.courseName}</td>
							<td>${tempScore.score}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<form method="GET" id="update-form" style="display: inline-block">
				<div class="form-group" style="display: flex; align-items: center">
					<label for="year" class="col-form-label" style="margin-right: 10px"
						>Year:
					</label>
					<select
						class="form-control"
						id="year"
						name="year"
						onchange="this.form.submit()"
					>
						<option value="all">Select Year</option>
						<c:forEach var="tempYear" items="${YEARS}">
							<option value="${tempYear}">${tempYear}</option>
						</c:forEach>
					</select>
				</div>
			</form>
		</div>
	</body>
</html>
