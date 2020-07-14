Create Table AttachmentBugReport (
	Primary key (attachment_id),
    attachment_id int NOT NULL,
	bug_report_id int NOT NULL,
    contents BYTEA
);

Create Table AttachmentLBTReport (
	Primary key (attachment_id),
    attachment_id int NOT NULL,
	lbt_report_id int NOT NULL,
    contents BYTEA
);

Create SEQUENCE attachment_bug_report_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By AttachmentBugReport.attachment_id
;

Create SEQUENCE attachment_lbt_report_sequence
AS int 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 999999
MINVALUE 1
NO CYCLE 
OWNED By AttachmentLBTReport.attachment_id
;