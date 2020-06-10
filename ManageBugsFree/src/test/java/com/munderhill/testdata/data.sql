Create SEQUENCE bug_reports_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By bugreports.bug_report_id
;

Create SEQUENCE lbt_reports_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By lbtreports.lbt_report_id
;

Create SEQUENCE bug_reports_additional_notes_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By bugreportadditionalnotes.br_note_id
;

Create SEQUENCE lbt_reports_additional_notes_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By lbtreportadditionalnotes.lbt_note_id
;

Create SEQUENCE br_similar_bug_reports_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By brsimilarbugreports.br_similar_bug_reports_id
;

Create SEQUENCE lbt_similar_bug_reports_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By lbtsimilarbugreports.lbt_similar_bug_reports_id
;

Create SEQUENCE bridlbtlink_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By bridlbtlink.brid_lbt_link_id
;

Create SEQUENCE users_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By users.user_id
;


Create Table BugReports (
	Primary key (bug_report_id),
    bug_report_id int NOT NULL,
    title varchar(100) NOT NULL,
	topic varchar(50) NOT NULL,
    team_name varchar(50) NOT NULL,
    user_assigned varchar(50),
	opened_by varchar(50) NOT NULL,
    priority varchar(15) NOT NULL,
    status varchar(55) NOT NULL,
    last_updated timestamp,
	updated_by varchar(50),
	date_created timestamp NOT NULL,
	date_first_closed timestamp,
    software_version varchar(20) NOT NULL,
    similar_bug_report_id int,
    abbreviated_stack_trace varchar(3000) NOT NULL,
    bug_description varchar(3000) NOT NULL
);

Create Table BugReportHistory (
    bug_report_id int NOT NULL,
    title varchar(100) NOT NULL,
	topic varchar(50) NOT NULL,
    lbt_report_id int,
    team_name varchar(50) NOT NULL,
    user_assigned varchar(50),
	opened_by varchar(50) NOT NULL,
    priority varchar(15) NOT NULL,
    status varchar(55) NOT NULL,
    last_updated timestamp,
	updated_by varchar(50),
	date_created timestamp NOT NULL,
	date_first_closed timestamp,
    software_version varchar(20) NOT NULL,
    similar_bug_report_id int,
    abbreviated_stack_trace varchar(3000) NOT NULL,
    bug_description varchar(3000) NOT NULL,
	start_date timestamp,
	end_date timestamp
);

Create Table LBTReports (
	Primary key (lbt_report_id),
    lbt_report_id int NOT NULL,
    title varchar(100) NOT NULL,
	topic varchar(50) NOT NULL,
    development_team varchar(50) NOT NULL,
    user_submitted varchar(50) NOT NULL,
	user_assigned varchar(50) NOT NULL,
	customer_support_team varchar(50) NOT NULL,
    severity_estimate varchar(15) NOT NULL,
    last_updated timestamp,
	updated_by varchar(50),
	date_created timestamp NOT NULL,
	date_all_linked_brs_closed timestamp,
    software_version varchar(20) NOT NULL,
    similar_bug_report_id int,
	linked_bug_report_id int,
    abbreviated_stack_trace varchar(3000) NOT NULL,
    bug_description varchar(3000) NOT NULL
);

Create Table LBTReportHistory (
    lbt_report_id int NOT NULL,
    title varchar(100) NOT NULL,
	topic varchar(50) NOT NULL,
    development_team varchar(50) NOT NULL,
    user_submitted varchar(50) NOT NULL,
	user_assigned varchar(50) NOT NULL,
	customer_support_team varchar(50) NOT NULL,
    severity_estimate varchar(15) NOT NULL,
    last_updated timestamp,
	updated_by varchar(50),
	date_created timestamp NOT NULL,
	date_all_linked_brs_closed timestamp,
    software_version varchar(20) NOT NULL,
    similar_bug_report_id int,
	linked_bug_report_id int,
    abbreviated_stack_trace varchar(3000) NOT NULL,
    bug_description varchar(3000) NOT NULL,
	start_date timestamp,
	end_date timestamp
);

Create Table BugReportAdditionalNotes (
	Primary key (br_note_id),
    br_note_id int NOT NULL,
	bug_report_id int NOT NULL,
	note varchar(3000) NOT NULL,
	entered_by_user_id int NOT NULL,
	entry_date timestamp NOT NULL
);

Create Table LBTReportAdditionalNotes (
	Primary key (lbt_note_id),
    lbt_note_id int NOT NULL,
	lbt_report_id int NOT NULL,
	note varchar(3000) NOT NULL,
	entered_by_user_id int NOT NULL,
	entry_date timestamp NOT NULL
);

Create Table Users (
	Primary key (user_id),
    user_id int NOT NULL,
    username Varchar(50) NOT NULL UNIQUE.
	team_name Varchar(50),
	role Varchar(50) NOT NULL
);

Create Table Teams (
	Primary key (team_id),
    team_id int NOT NULL,
    team_name Varchar(50) NOT NULL UNIQUE,
	team_type Varchar(50) NOT NULL,
);

Create Table TeamTypes (
	Primary key (team_type_id),
    team_type_id int NOT NULL,
	team_type Varchar(50) NOT NULL UNIQUE
);

Create Table Topics (
	Primary key (topic_id),
    topic_id int NOT NULL,
    topic_name Varchar(50) NOT NULL UNIQUE
);

Create Table Priorities (
	Primary key (priority_id),
    priority_id int NOT NULL,
    priority_description Varchar(50) NOT NULL UNIQUE
);

Create Table Statuses (
	Primary key (status_id),
    status_id int NOT NULL,
    status_description Varchar(55) NOT NULL UNIQUE
);

Create Table SoftwareVersions (
	Primary key (software_version_id),
    software_version_id int NOT NULL,
    software_version_description Varchar(50) NOT NULL UNIQUE
);

Create Table BRSimilarBugReports (
	Primary key (br_similar_bug_reports_id),
    bug_report_id integer NOT NULL REFERENCES bugreports(bug_report_id),
	bug_report_id int NOT NULL,
	similar_bug_report_id int NOT NULL
);

Create Table LBTSimilarBugReports (
	Primary key (lbt_similar_bug_reports_id),
    lbt_report_id integer NOT NULL REFERENCES lbtreports(lbt_report_id),
	lbt_report_id int NOT NULL,
	similar_bug_report_id int NOT NULL
);

Create Table BRIDLBTLink (
	Primary key (brid_lbt_link_id),
    brid_lbt_link_id int NOT NULL,
	bug_report_id int NOT NULL,
	lbt_report_id int NOT NULL
);

Insert Into Users(user_id, username, team_name)
VALUES(1, 'Unassigned', 'Unassigned');

Insert Into Teams(team_id,team_name)
VALUES(1, 'Unassigned', 'N/A'),
(2, 'Software Development Team 1' 'Software Development'),
(3, 'Software Development Team 2' 'Software Development'),
(4, 'Software Development Team 3' 'Software Development'),
(5, 'Customer Support Team 1', 'Customer Support'),
(6, 'Customer Support Team 2', 'Customer Support'),
(7, 'Customer Support Team 3', 'Customer Support');

Insert Into TeamTypes(team_type_id,team_type)
VALUES(1, 'Software Development'),
(2, 'Customer Support')

Insert Into Topics (topic_id,topic_name)
VALUES(1, 'Networking'),
(2, 'External Applications'),
(3, 'Internal Applications'),
(4, 'Database'),
(5, 'Server'),
(6, 'Distributed Network'),
(7, 'Web'),
(8, 'Messaging System'),
(9, 'Automated Systems')
(10, 'Unassigned');

Insert Into Priorities(priority_id,priority_description)
VALUES(1, '1'),
(2, '2'),
(3, '3'),
(4, '4'),
(5, '5');

Insert Into Statuses(status_id,status_description)
VALUES(1, 'Open'),
(2, 'Investigation In Progess'),
(3, 'Problem Identified - Not Resolved'),
(4, 'Problem Identified - Fix Ready - Needs Deployment'), 
(5, 'Problem Identified - Fix Ready - Deployment Scheduled'),
(6, 'Closed/Fix Deployed');

Insert Into SoftwareVersions(software_version_id,software_version_description)
VALUES(1, '1.0'),
(2, '1.1'),
(3, '1.2'),
(4, '2.0'),
(5, '2.1');

