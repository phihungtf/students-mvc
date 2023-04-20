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
		</style>
		<script>
			function toggleForm() {
				$('#search-form').toggleClass('hide');
				// focus on the input if the form is shown
				if (!$('#search-form').hasClass('hide')) {
					$('#search-form input').focus();
				}
			}

			function sort(type) {
				// add sort=type to the url
				var url = new URL(window.location.href);
				url.searchParams.set('sort', type);

				// reload the page
				window.location.href = url;
			}

			$(document).on('show.bs.modal', '#updateModal', function (event) {
				var button = $(event.relatedTarget); // Button that triggered the modal
				// var student = JSON.parse(button.data('student').replaceAll("'", '"')); // Extract info from data-* attributes
				var student = button.data('student'); // Extract info from data-* attributes
				if (student == null) {
					student = {};
				} else {
					student = JSON.parse(student.replaceAll("'", '"'));
				}
				console.log(student);

				var modal = $(this);
				if (student.id == null) {
					modal.find('.modal-title').text('Add student');
					$('#id', modal).val('');
					$('#name', modal).val('');
					$('#grade', modal).val('');
					$('#birthday', modal).val('');
					$('#notes', modal).val('');
					$('#action', modal).val('add');
				} else {
					modal.find('.modal-title').text('Update ' + student.name);
					$('#id', modal).val(student.id);
					$('#name', modal).val(student.name);
					$('#grade', modal).val(student.grade);
					$('#birthday', modal).val(student.birthday);
					$('#notes', modal).val(student.notes);
					$('#action', modal).val('update');
				}
			});

			$(document).on('close.bs.alert', '#alert', function (event) {
				var url = new URL(window.location.href);
				url.searchParams.delete('nameStr');

				// reload the page
				window.location.href = url;
			});
		</script>
	</head>

	<body>
		<nav class="navbar navbar-inverse" style="border-radius: 0px">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="./">Student Management</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="./student">Student</a></li>
					<li><a href="./course">Course</a></li>
				</ul>
			</div>
		</nav>
		<div class="container-fluid">
			<h1>Student List</h1>
			<c:if test="${not empty STUDENT_NAME}">
				<div class="alert alert-success" id="alert" role="alert">
					Result for <strong>${STUDENT_NAME}</strong>
					<button
						type="button"
						class="close"
						data-dismiss="alert"
						aria-label="Close"
					>
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>
			<c:if test="${not empty ERROR}">
				<div class="alert alert-danger" id="error" role="error">
					<strong>ERROR: </strong> ${ERROR}
					<button
						type="button"
						class="close"
						data-dismiss="alert"
						aria-label="Close"
					>
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Name</th>
						<th scope="col">Grade</th>
						<th scope="col">Birthday</th>
						<th scope="col">Notes</th>
						<th scope="col">Details</th>
						<th scope="col">Edit</th>
						<th scope="col">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tempStudent" items="${STUDENT_LIST}" varStatus="loop">
						<tr>
							<th scope="row">${loop.index + 1}</th>
							<td>${tempStudent.name}</td>
							<td>${tempStudent.grade}</td>
							<td>${tempStudent.birthday}</td>
							<td>${tempStudent.notes}</td>
							<td>
								<a class="btn btn-primary" href="./student/${tempStudent.id}"
									>Details</a
								>
							</td>
							<td>
								<button
									type="button"
									class="btn btn-success"
									data-toggle="modal"
									data-target="#updateModal"
									data-student="${tempStudent.toJSON()}"
								>
									Edit
								</button>
							</td>
							<td>
								<form method="POST">
									<input type="hidden" name="action" value="delete" />
									<input
										type="hidden"
										name="studentId"
										value="${tempStudent.id}"
									/>
									<button type="submit" class="btn btn-danger">Delete</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<div style="margin: 10px 0px 10px 0px; display: flex; gap: 12px">
				<button
					type="button"
					class="btn btn-primary"
					data-toggle="modal"
					data-target="#updateModal"
				>
					Add Student
				</button>
				<button class="btn btn-primary" onclick="sort('name');">
					Sort by name
				</button>
				<button class="btn btn-primary" onclick="sort('grade');">
					Sort by grade
				</button>
				<button class="btn btn-primary" onclick="toggleForm();">
					Find by name
				</button>
				<form id="search-form" method="GET" class="hide" style="display: flex">
					<input
						style="height: inherit; margin-right: 10px"
						type="text"
						class="form-control"
						id="nameStr"
						placeholder="Name"
						name="nameStr"
					/>
					<button type="submit" class="btn btn-primary">Find</button>
				</form>
			</div>

			<div
				class="modal fade"
				id="updateModal"
				tabindex="-1"
				role="dialog"
				aria-labelledby="updateModalLabel"
				aria-hidden="true"
			>
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title" id="updateModalLabel">Add student</h3>
							<button
								type="button"
								class="close"
								data-dismiss="modal"
								aria-label="Close"
							>
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<form method="POST" id="update-form">
								<div class="form-group">
									<label for="name" class="col-form-label">Name</label>
									<input
										type="text"
										class="form-control"
										id="name"
										placeholder="Name"
										name="name"
									/>
								</div>
								<div class="form-group">
									<label for="grade" class="col-form-label">Grade</label>
									<input
										type="number"
										class="form-control"
										id="grade"
										placeholder="Grade"
										name="grade"
									/>
								</div>
								<div class="form-group">
									<label for="birthday" class="col-form-label">Birthday</label>
									<input
										type="date"
										class="form-control"
										id="birthday"
										placeholder="Birthday"
										name="birthday"
									/>
								</div>
								<div class="form-group">
									<label for="notes" class="col-form-label">Notes</label>
									<input
										type="text"
										class="form-control"
										id="notes"
										placeholder="Notes"
										name="notes"
									/>
								</div>
								<input type="hidden" id="action" name="action" value="add" />
								<input type="hidden" id="id" name="id" value="" />
							</form>
						</div>
						<div class="modal-footer">
							<button
								type="button"
								class="btn btn-secondary"
								data-dismiss="modal"
							>
								Close
							</button>
							<button type="submit" form="update-form" class="btn btn-primary">
								Submit
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
