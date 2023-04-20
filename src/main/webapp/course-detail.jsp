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
		<script>
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
				if (!student.studentId) {
					modal.find('.modal-title').text('Add student');
					$('#id', modal).val('');
					$('#score', modal).val('');
					$('#action', modal).val('add');
				} else {
					modal.find('.modal-title').text('Update ' + student.studentName);
					$('#id', modal).val(student.studentId);
					$('#name', modal).append(
						$('<option>', {
							value: student.studentId,
							text: student.studentName,
							selected: true,
							'data-subtext': 'customize',
						})
					);
					$('#name').attr('disabled', true);
					$('#score', modal).val(student.score);
					$('#action', modal).val('update');
				}
			});

			$(document).on('hide.bs.modal', '#updateModal', function (event) {
				$('#name > option[data-subtext="customize"]', this).remove();
				$('#name').attr('disabled', false);
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
					<a class="navbar-brand" href="../">Student Management</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="../student">Student</a></li>
					<li class="active"><a href="../course">Course</a></li>
				</ul>
			</div>
		</nav>
		<div class="container-fluid">
			<h1>Course Information</h1>
			<div class="card col-md-12">
				<p class="card-title">
					<strong>Name: </strong
					><span class="badge badge-info">${COURSE.name}</span>
				</p>
				<p class="card-title">
					<strong>Lecturer: </strong
					><span class="badge badge-warning">${COURSE.lecturer}</span>
				</p>
				<p class="card-title">
					<strong>Year: </strong
					><span class="badge badge-success">${COURSE.year}</span>
				</p>
				<p class="card-title"><strong>Notes: </strong>${COURSE.notes}</p>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Name</th>
						<th scope="col">Score</th>
						<th scope="col">Edit</th>
						<th scope="col">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tempScore" items="${SCORES}" varStatus="loop">
						<tr>
							<th scope="row">${loop.index + 1}</th>
							<td>${tempScore.studentName}</td>
							<td>${tempScore.score}</td>
							<td>
								<button
									type="button"
									class="btn btn-success"
									data-toggle="modal"
									data-target="#updateModal"
									data-student="${tempScore.toJSON()}"
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
										value="${tempScore.studentId}"
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
				<button class="btn btn-primary" onclick="sort('score');">
					Sort by score
				</button>
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
									<select class="form-control" id="name" name="id">
										<c:forEach var="tempStudent" items="${STUDENTS}">
											<option value="${tempStudent.id}">
												${tempStudent.name}
											</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label for="score" class="col-form-label">Score</label>
									<input
										type="number"
										step="0.01"
										class="form-control"
										id="score"
										placeholder="Score"
										name="score"
									/>
								</div>
								<input type="hidden" id="action" name="action" value="add" />
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
		<script>
			$('#update-form').submit(function () {
				$('#name').attr('disabled', false);
			});
		</script>
	</body>
</html>
