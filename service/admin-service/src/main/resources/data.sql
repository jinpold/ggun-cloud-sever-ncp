-- select id from admins;


insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (1, 'ggunAdmin0001', 'pO2(eO73)%@', '김현주', '20050305', '경영기획본부', '부장', '경영기획본부장', 'jgs0318@gmail.com', '010-2212-0694', 'ROLE_ADMIN', '1', '2005-03-05 14:43:32', '2023-10-23 18:24:40');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (2, 'ggunAdmin0002', 'dlwldms123@', '제갈수', '20100607', '트레이딩본부', '차장', '트레이딩팀장', 'sjrkchdkdy@gmail.com', '010-4351-9876','ROLE_ADMIN', '2','2010-06-07 17:33:02', '2023-09-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (3, 'ggunAdmin0003', 'qkrwnsdud3@', '배광호', '20190506', '기업금융본부', '과장', '총괄금융1팀장','bgh950201@gmail.com', '010-3355-9088', 'ROLE_ADMIN', '3','2019-05-06 17:33:02', '2023-09-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (4, 'ggunAdmin0004', 'chlwldus12#', '허진',   '20160222', '증권사업본부', '대리', '미래전략관리팀원', 'jinpold@gmail.com', '010-1535-1177', 'ROLE_ADMIN', '4','2016-02-22 17:33:02', '2023-09-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (5, 'ggunAdmin0005', 'rladudg11#%', '이민재', '20240225', '파생영업본부', '사원', '국제영업관리' ,'alswodnjswo@gmail.com', '010-7612-9909', 'ROLE_ADMIN', '5','2024-02-25 17:33:02', '2023-09-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (6, 'ggunAdmin0006', 'dsfsdgs11#@', '양동규', '20150305', '경영기획본부', '대리', '경영기획팀원', 'yangdongGyu@gmail.com', '010-6887-3453', 'ROLE_ADMIN', '6','2015-03-05 17:33:02', '2023-09-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (7, 'ggunAdmin0007', 'sdfsdgg33##', '김호주', '20200607', '트레이딩본부', '사원', '트레이딩팀원', 'Hoojukim@gmail.com', '010-2345-2352', 'ROLE_ADMIN', '7','2020-06-07 17:33:02', '2024-11-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (8, 'ggunAdmin0008', 'jlkjfds98#@', '노태호', '20070501', '준법경영실', '실장', '준법경영총괄실장','Nohaeho@gmail.com', '010-8658-2532', 'ROLE_ADMIN', '8','2007-05-01 17:33:02', '2024-11-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (9, 'ggunAdmin0009', 'dsffsdff11#', '이건우',   '20180506', 'IB사업본부', '대리', '유동화금융1팀원', 'leeGunwoo@gmail.com', '010-2626-5476', 'ROLE_ADMIN', '9','2018-05-06 17:33:02', '2024-11-08 08:56:05');
insert into admins (id, username, password, name, number, department, position, job, email, phone, role, profile, mod_date, reg_date) values (10, 'ggunAdmin0010', 'fdsgsgmlsss2#', '이준휘', '20240225', '리스크관리실', '사원', '리스크관리담당' ,'6junhwi@gmail.com', '010-3466-3434','ROLE_ADMIN', '10','2024-02-25 17:33:02', '2024-11-08 08:56:05');



insert into admin_boards (id, title, description, mod_date, reg_date)
values (1, '사내 공지사항', '사내에서 공유할 중요한 공지사항을 게시합니다.', '2023-05-05 00:10:49', '2024-05-05 00:10:49');
insert into admin_boards (id, title, description, mod_date, reg_date)
values (2, '관리자문의', '관리자들이 시스템 관련 문의를 남기는 곳입니다.', '2023-09-30 08:20:52', '2023-12-05 00:12:51');

-- writer_id 1~5 (admin) / board_id 1 / 2 (사내 공지사항 / 관리자 문의)
insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (1, '시스템 점검 안내', '2024년 3월 1일 시스템 점검이 예정되어 있습니다. 서비스 중단 시간은 00:00~04:00입니다.', 2, 1, '2023-05-05 00:10:49', '2024-02-28 04:32:04');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (2, '사내 이벤트 공지', '2024년 2분기 우수 직원 포상 이벤트가 진행됩니다. 많은 참여 바랍니다.', 5, 1, '2023-04-14 12:07:46', '2023-09-30 08:20:52');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (3, '보안 정책 변경 안내', '사내 보안 정책이 변경되어 주기적인 비밀번호 변경이 필수화되었습니다.', 4, 1, '2024-01-16 19:23:29', '2024-01-29 03:11:26');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (4, '사내 워크샵 공지', '2024년 사내 워크샵이 4월에 개최될 예정입니다. 참가 신청은 HR부서로.', 3, 1, '2024-01-06 16:32:58', '2023-09-19 15:03:49');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (5, '서비스 업데이트 공지', '주요 서비스 업데이트 내역: 새로운 거래 기능 추가 및 버그 수정.', 1, 1, '2023-12-02 05:19:43', '2024-01-06 04:44:20');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (6, '새로운 사내 규정 안내', '새로운 사내 규정이 3월 1일부터 적용됩니다. 전 직원 필수 숙지 바랍니다.', 2, 1, '2023-04-13 23:03:48', '2024-03-10 03:25:24');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (7, '보안 점검 공지', '3월 10일 보안 점검으로 인한 시스템 일시 중단이 예정되어 있습니다.', 5, 1, '2023-11-15 12:45:14', '2024-03-02 11:22:39');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (8, '연말 사내 공지', '연말을 맞아 사내 규정 및 정책이 일부 변경되었습니다. 확인 부탁드립니다.', 3, 1, '2024-01-10 09:13:24', '2023-12-18 06:29:52');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (9, '긴급 시스템 공지', '예기치 못한 서버 문제로 인해 서비스 중단이 발생했습니다. 빠른 복구를 위해 노력 중입니다.', 4, 1, '2024-01-28 14:45:36', '2023-11-22 12:35:21');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (10, '신규 서비스 출시 안내', '2024년 4월부터 새로운 금융 서비스를 제공합니다. 자세한 내용은 추후 공지될 예정입니다.', 2, 1, '2024-01-15 13:54:11', '2024-01-25 09:14:03');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (11, '임시 휴무 안내', '내부 시스템 점검으로 인해 4월 10일 임시 휴무가 예정되어 있습니다.', 1, 1, '2023-10-07 18:25:16', '2024-02-15 13:45:28');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (12, '기타 공지 사항', '기타 중요한 공지 사항이 있을 경우 여기에서 확인하실 수 있습니다.', 5, 1, '2023-07-20 22:33:48', '2024-03-12 08:47:39');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (13, '사내 연말 파티 안내', '2024년 연말 파티가 12월 25일 개최됩니다. 많은 참여 부탁드립니다.', 4, 1, '2023-11-25 17:02:28', '2024-01-05 15:18:54');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (14, '2024년 목표 및 전략 발표', '2024년 목표 및 전략 발표가 3월 20일에 있을 예정입니다. 자세한 내용은 추후 공지.', 3, 1, '2023-12-08 10:11:39', '2024-02-27 07:32:29');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (15, '내부 감사 안내', '2024년 상반기 내부 감사가 4월 1일부터 시작됩니다. 관련 부서 준비 바랍니다.', 2, 1, '2023-11-14 09:56:12', '2024-01-28 12:04:49');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (16, '시스템 접근 오류 문의', '시스템 접속 시 "권한 없음" 오류 발생합니다. 확인 부탁드립니다.', 3, 2, '2023-08-22 22:35:11', '2023-07-06 06:42:17');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (17, '로그인 문제 발생', '로그인 시도 시 비밀번호가 정확함에도 불구하고 실패합니다.', 4, 2, '2024-01-09 17:52:26', '2024-01-03 06:33:31');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (18, '계정 잠금 관련 문의', '비밀번호 5회 이상 틀려 계정이 잠겼습니다. 해제 요청드립니다.', 5, 2, '2024-01-31 10:31:58', '2023-08-24 06:03:54');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (19, '시스템 성능 개선 요청', '현재 시스템 성능이 저하된 상태입니다. 서버 증설이 필요해 보입니다.', 3, 2, '2023-06-19 23:44:50', '2023-09-25 18:22:19');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (20, '거래 기능 오류 문의', '거래 중 특정 기능이 작동하지 않습니다. 버그 여부 확인 부탁드립니다.', 2, 2, '2023-07-28 16:21:23', '2024-02-28 09:10:53');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (21, '보안 설정 변경 요청', '특정 IP 주소에서만 접근 가능하도록 보안 설정을 강화하고 싶습니다.', 1, 2, '2024-03-11 00:53:52', '2023-12-08 13:55:58');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (22, '시스템 유지보수 일정 문의', '시스템 유지보수 일정이 궁금합니다. 일정 공유 부탁드립니다.', 5, 2, '2023-11-10 15:22:42', '2023-06-06 14:24:05');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (23, '관리자 페이지 기능 추가 요청', '관리자 페이지에 신규 기능 추가를 요청드립니다. (예: 사용자 로그 기록 확인)', 4, 2, '2023-12-22 18:46:27', '2023-07-21 05:30:17');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (24, '권한 설정 관련 문의', '특정 사용자에 대해 권한 설정이 제대로 적용되지 않는 것 같습니다.', 3, 2, '2024-01-31 10:31:58', '2023-08-24 06:03:54');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (25, '계정 복구 요청', '삭제된 계정을 복구할 수 있는 방법이 있나요? 방법이 있다면 알려주세요.', 2, 2, '2024-01-07 11:04:31', '2023-12-16 19:12:41');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (26, '데이터 백업 문제', '데이터 백업 중 일부 오류가 발생했습니다. 다시 시도해도 같은 문제가 발생합니다.', 1, 2, '2023-09-19 10:29:47', '2023-11-15 20:14:09');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (27, '서버 오류 문의', '서버 응답 속도가 비정상적으로 느립니다. 원인 파악 후 조치 바랍니다.', 5, 2, '2024-01-19 08:37:15', '2023-10-05 12:53:08');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (28, '사용자 권한 부여 요청', '신규 사용자 계정에 대한 권한 부여 요청드립니다.', 4, 2, '2024-01-12 07:51:46', '2023-08-28 10:11:56');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (29, '관리자 계정 정보 수정 요청', '관리자 계정 정보 수정이 필요합니다. 관련 문서 제출 후 요청드립니다.', 3, 2, '2023-11-05 22:18:33', '2023-10-15 08:17:23');

insert into admin_articles (id, title, content, writer_id, board_id, mod_date, reg_date)
values (30, '시스템 복구 관련 문의', '예기치 않은 장애로 인해 시스템이 다운되었습니다. 복구 방법에 대해 문의드립니다.', 2, 2, '2023-09-18 15:26:48', '2024-02-22 12:30:58');