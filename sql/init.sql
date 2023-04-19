CREATE TABLE `students`(
    `id` BIGINT UNSIGNED NOT NULL,
    `name` TEXT NOT NULL,
    `grade` INT NOT NULL,
    `birthday` DATE NOT NULL,
    `notes` TEXT
);
ALTER TABLE
    `students` ADD PRIMARY KEY(`id`);
ALTER TABLE
    `students` CHANGE `id` `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

CREATE TABLE `score_board`(
    `student_id` BIGINT UNSIGNED NOT NULL,
    `course_id` BIGINT NOT NULL,
    `score` DOUBLE(8, 2)
);
ALTER TABLE
    `score_board` ADD PRIMARY KEY(`student_id`, `course_id`);

CREATE TABLE `courses`(
    `id` BIGINT UNSIGNED NOT NULL,
    `name` TEXT NOT NULL,
    `lecturer` TEXT NOT NULL,
    `year` YEAR NOT NULL,
    `notes` TEXT
);
ALTER TABLE
    `courses` ADD PRIMARY KEY(`id`);
ALTER TABLE
    `courses` CHANGE `id` `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE
    `score_board` ADD CONSTRAINT `score_board_course_id_foreign` FOREIGN KEY(`course_id`) REFERENCES `courses`(`id`);
ALTER TABLE
    `score_board` ADD CONSTRAINT `score_board_student_id_foreign` FOREIGN KEY(`student_id`) REFERENCES `students`(`id`);