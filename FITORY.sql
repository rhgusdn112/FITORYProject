CREATE TABLE "ADMIN" (
	"MANAGER_NO"	NUMBER		NOT NULL,
	"EMAIL"	VARCHAR2(50)		NOT NULL,
	"PW"	VARCHAR2(100)		NOT NULL,
	"NICKNAME"	VARCHAR2(10)		NOT NULL,
	"DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"AUTHORITY"	NUMBER	DEFAULT '1'	NOT NULL,
	"AUTHORITY_MEMBER"	NUMBER	DEFAULT '2'	NOT NULL,
	"AUTHORITY_TRAINNER"	NUMBER	DEFAULT '2'	NOT NULL
);

COMMENT ON COLUMN "ADMIN"."MANAGER_NO" IS '회원번호(SEQ)';

COMMENT ON COLUMN "ADMIN"."EMAIL" IS '관리자 메일';

COMMENT ON COLUMN "ADMIN"."PW" IS '관리자 비밀번호';

COMMENT ON COLUMN "ADMIN"."NICKNAME" IS '관리자명';

COMMENT ON COLUMN "ADMIN"."DEL_FL" IS '탈퇴여부';

COMMENT ON COLUMN "ADMIN"."AUTHORITY" IS '관리자 권한(1:기본, 2: 관리자)';

COMMENT ON COLUMN "ADMIN"."AUTHORITY_MEMBER" IS '회원관리 권한';

COMMENT ON COLUMN "ADMIN"."AUTHORITY_TRAINNER" IS '강사관리 권한';

CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"MEMBER_EMAIL"	VARCHAR2(50)		NOT NULL,
	"MEMBER_PW"	VARCHAR2(100)		NOT NULL,
	"MEMBER_NICKNAME"	VARCHAR2(10)		NOT NULL,
	"MEMBER_TEL"	CHAR(11)		NOT NULL,
	"MEMBER_ADDRESS"	VARCHAR2(50)		NULL,
	"PROFILE_IMG"	VARCHAR2(50)		NULL,
	"HEIGHT"	NUMBER		NULL,
	"WEIGHT"	NUMBER		NULL,
	"ENROOL_DATE"	DATE	DEFAULT CURRENT_DATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"AUTHORITY"	NUMBER	DEFAULT 1	NOT NULL
);

COMMENT ON COLUMN "MEMBER"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL" IS '회원 이메일';

COMMENT ON COLUMN "MEMBER"."MEMBER_PW" IS '회원 비밀번호(암호화)';

COMMENT ON COLUMN "MEMBER"."MEMBER_NICKNAME" IS '회원명';

COMMENT ON COLUMN "MEMBER"."MEMBER_TEL" IS '회원 전화번호(-제외)';

COMMENT ON COLUMN "MEMBER"."MEMBER_ADDRESS" IS '회원 주소';

COMMENT ON COLUMN "MEMBER"."PROFILE_IMG" IS '프로필 이미지 경로';

COMMENT ON COLUMN "MEMBER"."HEIGHT" IS '회원 신장';

COMMENT ON COLUMN "MEMBER"."WEIGHT" IS '회원 몸무게';

COMMENT ON COLUMN "MEMBER"."ENROOL_DATE" IS '가입일';

COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL" IS '탈퇴여부('Y','N')';

COMMENT ON COLUMN "MEMBER"."AUTHORITY" IS '권한(1:일반, 2:관리자)';

CREATE TABLE "BOARD" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"TITLE"	VARCHAR2(50)		NOT NULL,
	"DETAIL"	VARCHAR2(4000)		NOT NULL,
	"HITS"	NUMBER	DEFAULT 0	NOT NULL,
	"UPLOAD_DATE"	DATE	DEFAULT CURRENT_DATE	NOT NULL,
	"BILLING_DATE"	DATE	DEFAULT CURRENT_DATE	NOT NULL,
	"GRADE"	NUMBER	NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"TRAINER_NO"	NUMBER		NOT NULL,
	"CLASS_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '강의번호';

COMMENT ON COLUMN "BOARD"."TITLE" IS '강의 제목';

COMMENT ON COLUMN "BOARD"."DETAIL" IS '강의 내용';

COMMENT ON COLUMN "BOARD"."HITS" IS '조회수';

COMMENT ON COLUMN "BOARD"."UPLOAD_DATE" IS '작성일';

COMMENT ON COLUMN "BOARD"."BILLING_DATE" IS '마지막 수정일';

COMMENT ON COLUMN "BOARD"."GRADE" IS '평점';

COMMENT ON COLUMN "BOARD"."BOARD_DEL_FL" IS '강의 삭제여부(Y,N)';

COMMENT ON COLUMN "BOARD"."TRAINER_NO" IS '강사번호';

COMMENT ON COLUMN "BOARD"."CLASS_NO" IS '게시판 번호';

CREATE TABLE "TRAINER" (
	"TRAINER_NO"	NUMBER		NOT NULL,
	"TRAINER_EMAIL"	VARCHAR2(50)		NOT NULL,
	"TRAINER_PW"	VARCHAR2(100)		NOT NULL,
	"TRAINER_NICKNAME"	VARCHAR2(10)		NOT NULL,
	"TRAINER_TEL"	CHAR(11)		NOT NULL,
	"TRAINER_ADDRESS"	VARCHAR2(50)		NULL,
	"PROFILE_IMG"	VARCHAR2(50)		NULL,
	"ENROLL_DATE"	DATE	DEFAULT CURRENT_DATE	NOT NULL,
	"TRAINNER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"AUTHORITY"	NUMBER	DEFAULT 1	NOT NULL
);

COMMENT ON COLUMN "TRAINER"."TRAINER_NO" IS '강사번호';

COMMENT ON COLUMN "TRAINER"."TRAINER_EMAIL" IS '강사 이메일';

COMMENT ON COLUMN "TRAINER"."TRAINER_PW" IS '강사 비밀번호(암호화)';

COMMENT ON COLUMN "TRAINER"."TRAINER_NICKNAME" IS '강사명';

COMMENT ON COLUMN "TRAINER"."TRAINER_TEL" IS '강사 전화번호';

COMMENT ON COLUMN "TRAINER"."TRAINER_ADDRESS" IS '강사 주소';

COMMENT ON COLUMN "TRAINER"."PROFILE_IMG" IS '프로필 이미지 경로';

COMMENT ON COLUMN "TRAINER"."ENROLL_DATE" IS '가입일';

COMMENT ON COLUMN "TRAINER"."TRAINNER_DEL_FL" IS '탈퇴여부(Y,N)';

COMMENT ON COLUMN "TRAINER"."AUTHORITY" IS '권한(1:강사., 2:관리자)';

CREATE TABLE "CLASS" (
	"CLASS_NO"	NUMBER		NOT NULL,
	"THUMBNAIL_IMG"	VARCHAR2(4000)		NULL,
	"TRAINER_NO"	NUMBER		NOT NULL,
	"CATAGORY_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "CLASS"."CLASS_NO" IS '게시판 번호';

COMMENT ON COLUMN "CLASS"."THUMBNAIL_IMG" IS '썸네일 이미지';

COMMENT ON COLUMN "CLASS"."TRAINER_NO" IS '작성자';

COMMENT ON COLUMN "CLASS"."CATAGORY_NO" IS '분류';

CREATE TABLE "SALES" (
	"PAY"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "SALES"."PAY" IS '가격';

COMMENT ON COLUMN "SALES"."BOARD_NO" IS '강의번호';

CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONTENT"	VARCHAR2(400)		NOT NULL,
	"COMMENT_WRITE_DATE"	DATE		NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"PARENT_COMMENT_NO"	NUMBER		NULL,
	"GRADE"	NUMBER	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"CLASS_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS '댓글번호(SEQ_COMMENT_NO)';

COMMENT ON COLUMN "COMMENT"."COMMENT_CONTENT" IS '댓글 내용';

COMMENT ON COLUMN "COMMENT"."COMMENT_WRITE_DATE" IS '댓글 작성일';

COMMENT ON COLUMN "COMMENT"."BOARD_DEL_FL" IS '삭제여부(Y,N)';

COMMENT ON COLUMN "COMMENT"."PARENT_COMMENT_NO" IS '부모 댓글번호';

COMMENT ON COLUMN "COMMENT"."GRADE" IS '평점';

COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "COMMENT"."CLASS_NO" IS '강의번호';

CREATE TABLE "SUBSCRIPT" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"TRAINNER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "SUBSCRIPT"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "SUBSCRIPT"."BOARD_NO" IS '강의 번호';

COMMENT ON COLUMN "SUBSCRIPT"."TRAINNER_NO" IS '강사번호';

CREATE TABLE "CATAGORY" (
	"CATAGORY_NO"	NUMBER		NOT NULL,
	"CATAGORY_NAME"	CHAR(1)		NOT NULL
);

COMMENT ON COLUMN "CATAGORY"."CATAGORY_NAME" IS '카테고리 종류';

CREATE TABLE "BASKET" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"PAY"	NUMBER	DEFAULT 1000	NOT NULL
);

COMMENT ON COLUMN "BASKET"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "BASKET"."BOARD_NO" IS '강의번호';

COMMENT ON COLUMN "BASKET"."PAY" IS '가격';

CREATE TABLE "DETAIL" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"TRAINER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "DETAIL"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "DETAIL"."BOARD_NO" IS '강의 번호';

COMMENT ON COLUMN "DETAIL"."TRAINER_NO" IS '강사번호';

CREATE TABLE "MEMBER_ADMIN" (
	"MANAGER_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "MEMBER_ADMIN"."MANAGER_NO" IS '회원번호(SEQ)';

COMMENT ON COLUMN "MEMBER_ADMIN"."MEMBER_NO" IS '회원번호';

CREATE TABLE "TRAINER_ADMIN" (
	"MANAGER_NO"	NUMBER		NOT NULL,
	"TRAINER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "TRAINER_ADMIN"."MANAGER_NO" IS '회원번호(SEQ)';

COMMENT ON COLUMN "TRAINER_ADMIN"."TRAINER_NO" IS '강사번호';

ALTER TABLE "ADMIN" ADD CONSTRAINT "PK_ADMIN" PRIMARY KEY (
	"MANAGER_NO"
);

ALTER TABLE "MEMBER" ADD CONSTRAINT "PK_MEMBER" PRIMARY KEY (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "PK_BOARD" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "TRAINER" ADD CONSTRAINT "PK_TRAINER" PRIMARY KEY (
	"TRAINER_NO"
);

ALTER TABLE "CLASS" ADD CONSTRAINT "PK_CLASS" PRIMARY KEY (
	"CLASS_NO"
);

ALTER TABLE "SALES" ADD CONSTRAINT "PK_SALES" PRIMARY KEY (
	"PAY"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "PK_COMMENT" PRIMARY KEY (
	"COMMENT_NO"
);

ALTER TABLE "SUBSCRIPT" ADD CONSTRAINT "PK_SUBSCRIPT" PRIMARY KEY (
	"MEMBER_NO",
	"BOARD_NO",
	"TRAINNER_NO"
);

ALTER TABLE "CATAGORY" ADD CONSTRAINT "PK_CATAGORY" PRIMARY KEY (
	"CATAGORY_NO"
);

ALTER TABLE "BASKET" ADD CONSTRAINT "PK_BASKET" PRIMARY KEY (
	"MEMBER_NO",
	"BOARD_NO",
	"PAY"
);

ALTER TABLE "DETAIL" ADD CONSTRAINT "PK_DETAIL" PRIMARY KEY (
	"MEMBER_NO",
	"BOARD_NO",
	"TRAINER_NO"
);

ALTER TABLE "MEMBER_ADMIN" ADD CONSTRAINT "PK_MEMBER_ADMIN" PRIMARY KEY (
	"MANAGER_NO",
	"MEMBER_NO"
);

ALTER TABLE "TRAINER_ADMIN" ADD CONSTRAINT "PK_TRAINER_ADMIN" PRIMARY KEY (
	"MANAGER_NO",
	"TRAINER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_TRAINER_TO_BOARD_1" FOREIGN KEY (
	"TRAINER_NO"
)
REFERENCES "TRAINER" (
	"TRAINER_NO"
);


ALTER TABLE "BOARD" ADD CONSTRAINT "FK_CLASS_TO_BOARD_1" FOREIGN KEY (
	"CLASS_NO"
)
REFERENCES "CLASS" (
	"CLASS_NO"
);

ALTER TABLE "CLASS" ADD CONSTRAINT "FK_TRAINER_TO_CLASS_1" FOREIGN KEY (
	"TRAINER_NO"
)
REFERENCES "TRAINER" (
	"TRAINER_NO"
);

ALTER TABLE "CLASS" ADD CONSTRAINT "FK_CATAGORY_TO_CLASS_1" FOREIGN KEY (
	"CATAGORY_NO"
)
REFERENCES "CATAGORY" (
	"CATAGORY_NO"
);

ALTER TABLE "SALES" ADD CONSTRAINT "FK_BOARD_TO_SALES_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_MEMBER_TO_COMMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_BOARD_TO_COMMENT_1" FOREIGN KEY (
	"CLASS_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "SUBSCRIPT" ADD CONSTRAINT "FK_MEMBER_TO_SUBSCRIPT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "SUBSCRIPT" ADD CONSTRAINT "FK_BOARD_TO_SUBSCRIPT_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "SUBSCRIPT" ADD CONSTRAINT "FK_TRAINER_TO_SUBSCRIPT_1" FOREIGN KEY (
	"TRAINNER_NO"
)
REFERENCES "TRAINER" (
	"TRAINER_NO"
);

ALTER TABLE "BASKET" ADD CONSTRAINT "FK_MEMBER_TO_BASKET_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BASKET" ADD CONSTRAINT "FK_BOARD_TO_BASKET_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "BASKET" ADD CONSTRAINT "FK_SALES_TO_BASKET_1" FOREIGN KEY (
	"PAY"
)
REFERENCES "SALES" (
	"PAY"
);

ALTER TABLE "DETAIL" ADD CONSTRAINT "FK_MEMBER_TO_DETAIL_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "DETAIL" ADD CONSTRAINT "FK_SUBSCRIPT_TO_DETAIL_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "SUBSCRIPT" (
	"BOARD_NO"
);

ALTER TABLE "DETAIL" ADD CONSTRAINT "FK_SUBSCRIPT_TO_DETAIL_2" FOREIGN KEY (
	"TRAINER_NO"
)
REFERENCES "SUBSCRIPT" (
	"TRAINNER_NO"
);

ALTER TABLE "MEMBER_ADMIN" ADD CONSTRAINT "FK_ADMIN_TO_MEMBER_ADMIN_1" FOREIGN KEY (
	"MANAGER_NO"
)
REFERENCES "ADMIN" (
	"MANAGER_NO"
);

ALTER TABLE "MEMBER_ADMIN" ADD CONSTRAINT "FK_MEMBER_TO_MEMBER_ADMIN_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "TRAINER_ADMIN" ADD CONSTRAINT "FK_ADMIN_TO_TRAINER_ADMIN_1" FOREIGN KEY (
	"MANAGER_NO"
)
REFERENCES "ADMIN" (
	"MANAGER_NO"
);

ALTER TABLE "TRAINER_ADMIN" ADD CONSTRAINT "FK_TRAINER_TO_TRAINER_ADMIN_1" FOREIGN KEY (
	"TRAINER_NO"
)
REFERENCES "TRAINER" (
	"TRAINER_NO"
);
