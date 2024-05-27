-- 멤버 테이블 더미 데이터
INSERT INTO tbl_member VALUES ('user1', 'password1', 'User One', 'user1@example.com', '01011111111', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'man', '06035 서울 강남구 가로수길 5가나다, 1234 (신사동)', SYSDATE, '1');
INSERT INTO tbl_member VALUES ('user2', 'password2', 'User Two', 'user2@example.com', '01022222222', TO_DATE('1991-02-02', 'YYYY-MM-DD'), 'woman', '06210 서울 강남구 역삼동 123-45', SYSDATE, '1');
INSERT INTO tbl_member VALUES ('user3', 'password3', 'User Three', 'user3@example.com', '01033333333', TO_DATE('1992-03-03', 'YYYY-MM-DD'), 'man', '04347 서울 용산구 한남동 123-45', SYSDATE, '1');
INSERT INTO tbl_member VALUES ('user4', 'password4', 'User Four', 'user4@example.com', '01044444444', TO_DATE('1993-04-04', 'YYYY-MM-DD'), 'woman', '06067 서울 강남구 역삼동 123-45', SYSDATE, '1');
INSERT INTO tbl_member VALUES ('user5', 'password5', 'User Five', 'user5@example.com', '01055555555', TO_DATE('1994-05-05', 'YYYY-MM-DD'), 'man', '06234 서울 강남구 신사동 123-45', SYSDATE, '1');
INSERT INTO tbl_member VALUES ('admin6', 'password6', 'Admin Six', 'admin6@example.com', '01066666666', TO_DATE('1995-06-06', 'YYYY-MM-DD'), 'man', '06205 서울 강남구 청담동 123-45', SYSDATE, '2');
INSERT INTO tbl_member VALUES ('admin7', 'password7', 'Admin Seven', 'admin7@example.com', '01077777777', TO_DATE('1996-07-07', 'YYYY-MM-DD'), 'woman', '06023 서울 강남구 논현동 123-45', SYSDATE, '2');
INSERT INTO tbl_member VALUES ('admin8', 'password8', 'Admin Eight', 'admin8@example.com', '01088888888', TO_DATE('1997-08-08', 'YYYY-MM-DD'), 'man', '06312 서울 강남구 삼성동 123-45', SYSDATE, '2');
INSERT INTO tbl_member VALUES ('admin9', 'password9', 'Admin Nine', 'admin9@example.com', '01099999999', TO_DATE('1998-09-09', 'YYYY-MM-DD'), 'woman', '06110 서울 강남구 역삼동 123-45', SYSDATE, '2');
INSERT INTO tbl_member VALUES ('admin10', 'password10', 'Admin Ten', 'admin10@example.com', '01010101010', TO_DATE('1999-10-10', 'YYYY-MM-DD'), 'man', '06036 서울 강남구 신사동 123-45', SYSDATE, '2');

-- 상품 테이블 더미 데이터
INSERT INTO tbl_product VALUES (1, 'admin1', 'top', '상의 1', 30000, '블랙', 'S', '상의 1 상품 설명', 16);
INSERT INTO tbl_product VALUES (2, 'admin1', 'top', '상의 2', 35000, '화이트', 'M', '상의 2 상품 설명', 16);
INSERT INTO tbl_product VALUES (3, 'admin1', 'top', '상의 3', 32000, '베이지', 'L', '상의 3 상품 설명', 16);
INSERT INTO tbl_product VALUES (4, 'admin1', 'top', '상의 4', 28000, '블랙', 'S', '상의 4 상품 설명', 16);
INSERT INTO tbl_product VALUES (5, 'admin1', 'acc', '악세서리 1', 15000, '화이트', 'M', '악세서리 1 상품 설명', 16);
INSERT INTO tbl_product VALUES (6, 'admin1', 'acc', '악세서리 2', 20000, '베이지', 'L', '악세서리 2 상품 설명', 16);
INSERT INTO tbl_product VALUES (7, 'admin1', 'acc', '악세서리 3', 18000, '블랙', 'S', '악세서리 3 상품 설명', 16);
INSERT INTO tbl_product VALUES (8, 'admin1', 'bottom', '하의 1', 40000, '화이트', 'M', '하의 1 상품 설명', 16);
INSERT INTO tbl_product VALUES (9, 'admin1', 'bottom', '하의 2', 38000, '블랙', 'L', '하의 2 상품 설명', 16);
INSERT INTO tbl_product VALUES (10, 'admin1', 'bottom', '하의 3', 42000, '베이지', 'S', '하의 3 상품 설명', 16);

-- 이미지 테이블 더미 데이터 
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (1, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\1');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (2, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\2');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (3, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\3');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (4, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\4');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (5, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\5');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (6, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\6');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (7, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\7');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (8, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\8');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (9, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\9');
INSERT INTO tbl_img (i_pidx, i_name, i_path) VALUES (10, 'jewel.jpg', 'C:\TeamProject\Project39\src\main\webapp\productimage\10');


-- 장바구니 테이블 더미 데이터 
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (1, 1, 'user1', '상의 1', 1, 30000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (2, 2, 'user1', '상의 2', 1, 35000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (3, 3, 'user1', '상의 3', 1, 32000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (4, 4, 'user1', '상의 4', 1, 28000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (5, 5, 'user1', '악세서리 1', 1, 15000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (6, 6, 'user1', '악세서리 2', 1, 20000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (7, 7, 'user1', '악세서리 3', 1, 18000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (8, 8, 'user1', '하의 1', 1, 40000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (9, 9, 'user1', '하의 2', 1, 38000);
INSERT INTO tbl_cart (c_idx, c_pidx, c_mid, c_name, c_amount, c_price) VALUES (10, 10, 'user1', '하의 3', 1, 42000);

-- 결제 테이블 더미 데이터
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('1', 'user1', 30000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('2', 'user1', 35000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('3', 'user1', 32000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('4', 'user1', 28000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('5', 'user1', 15000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('6', 'user1', 20000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('7', 'user1', 18000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('8', 'user1', 40000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('9', 'user1', 38000, SYSDATE);
INSERT INTO tbl_pay (p_idx, p_mid, p_price, p_date) VALUES ('10', 'user1', 42000, SYSDATE);

-- 주문 테이블 더미 데이터
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1231', 'user1', 'User One', '06035 서울 강남구 가로수길 5가나다, 1234 (신사동)', '01011111111', '특별히 없습니다.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1232', 'user1', 'User One', '06210 서울 강남구 역삼동 123-45', '01022222222', '문 앞에 놓아주세요.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1233', 'user1', 'User One', '04347 서울 용산구 한남동 123-45', '01033333333', '부재 시 연락 주세요.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1234', 'user1', 'User One', '06067 서울 강남구 역삼동 123-45', '01044444444', '전화 먼저 해주세요.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1235', 'user1', 'User One', '06234 서울 강남구 신사동 123-45', '01055555555', '부재 시 경비실에 맡겨주세요.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1236', 'user1', 'User One', '06205 서울 강남구 청담동 123-45', '01066666666', '빨리 배송해주세요.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1237', 'user1', 'User One', '06023 서울 강남구 논현동 123-45', '01077777777', '상품 확인 후 결제 부탁드립니다.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1238', 'user1', 'User One', '06312 서울 강남구 삼성동 123-45', '01088888888', '포장이 완전히 되어 있는지 확인해주세요.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP1239', 'user1', 'User One', '06110 서울 강남구 역삼동 123-45', '01099999999', '주말에 배송이 가능한지 확인해주세요.');
INSERT INTO tbl_order (o_idx, o_mid, o_name, o_address1, o_hp, o_request) 
VALUES ('IMP12310', 'user1', 'User One', '06036 서울 강남구 신사동 123-45', '01010101010', '유리제품이라 조심히 다뤄주세요.');


-- 구매 테이블 더미 데이터
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (1, 'IMP1231', '상의 1', 30000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (2, 'IMP1232', '상의 2', 35000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (3, 'IMP1233', '상의 3', 32000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (4, 'IMP1234', '상의 4', 28000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (5, 'IMP1235', '악세서리 1', 15000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (6, 'IMP1236', '악세서리 2', 20000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (7, 'IMP1237', '악세서리 3', 18000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (8, 'IMP1238', '하의 1', 40000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (9, 'IMP1239', '하의 2', 38000, 'admin1', SYSDATE);
INSERT INTO tbl_buy (b_idx, b_oidx, b_name, b_price, b_pmid, b_date) 
VALUES (10, 'IMP12310', '하의 3', 42000, 'admin1', SYSDATE);

-- 공지사항 테이블 더미 데이터
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (1, '공지 제목 1', '이번 달 예정된 이벤트 안내드립니다.', SYSDATE, 0, 'file1.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (2, '공지 제목 2', '내일부터 새로운 할인 행사가 시작됩니다.', SYSDATE, 0, 'file2.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (3, '공지 제목 3', '이번 주 특별 이벤트가 개최됩니다. 많은 관심 부탁드립니다.', SYSDATE, 0, 'file3.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (4, '공지 제목 4', '쇼핑몰 이용 안내입니다. 유의하여 주시기 바랍니다.', SYSDATE, 0, 'file4.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (5, '공지 제목 5', '오늘 오픈하는 신제품 소식입니다.', SYSDATE, 0, 'file5.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (6, '공지 제목 6', '이번 달에 할인 이벤트가 추가됩니다.', SYSDATE, 0, 'file6.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (7, '공지 제목 7', '쇼핑몰에서 새로운 상품이 출시되었습니다.', SYSDATE, 0, 'file7.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (8, '공지 제목 8', '이번 달 회원 혜택을 안내합니다.', SYSDATE, 0, 'file8.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (9, '공지 제목 9', '새로운 이벤트 안내입니다. 많은 관심 부탁드립니다.', SYSDATE, 0, 'file9.png', 'admin1');
INSERT INTO tbl_news (n_idx, n_title, n_content, n_date, n_cnt, n_sfile, n_mid) 
VALUES (10, '공지 제목 10', '이번 달 쇼핑몰 이용 안내입니다.', SYSDATE, 0, 'file10.png', 'admin1');

-- QnA 테이블 더미 데이터
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (1, '배송문의', '이 상품에 대한 문의사항이 있습니다.', 0, 0, SYSDATE, 0, 'file1.png', 'user1', 1);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (2, '결제문의', '배송일정이 궁금합니다.', 0, 0, SYSDATE, 0, 'file2.png', 'user1', 2);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (3, '상품문의', '상품에 대한 사이즈 문의입니다.', 0, 0, SYSDATE, 0, 'file3.png', 'user1', 3);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (4, '취소문의', '상품 리뷰를 작성하고 싶습니다.', 0, 0, SYSDATE, 0, 'file4.png', 'user1', 4);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (5, '배송문의', '반품 관련 문의입니다.', 0, 0, SYSDATE, 0, 'file5.png', 'user1', 5);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (6, '결제문의', '할인 혜택에 대해 문의드립니다.', 0, 0, SYSDATE, 0, 'file6.png', 'user1', 6);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (7, '상품문의', '상품에 대한 포장이 어떻게 되어있나요?', 0, 0, SYSDATE, 0, 'file7.png', 'user1', 7);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (8, '취소문의', '상품 관련 문의사항입니다.', 0, 0, SYSDATE, 0, 'file8.png', 'user1', 8);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (9, '배송문의', '상품을 구매하고 싶습니다. 어떻게 해야 하나요?', 0, 0, SYSDATE, 0, 'file9.png', 'user1', 9);
INSERT INTO tbl_qna (q_idx, q_title, q_content, q_group, q_level, q_date, q_cnt, q_sfile, q_mid, q_bidx) 
VALUES (10, '결제문의', '배송지 변경이 가능한가요?', 0, 0, SYSDATE, 0, 'file10.png', 'user1', 10);

-- 리뷰 테이블 더미 데이터
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (1, '좋아요', '상품이 매우 만족스럽습니다.', SYSDATE, 'review1.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (2, '추천해요', '가격 대비 품질이 좋습니다.', SYSDATE, 'review2.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (3, '만족합니다', '배송이 빨라서 좋았어요.', SYSDATE, 'review3.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (4, '적극 추천', '다음에도 이용하고 싶습니다.', SYSDATE, 'review4.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (5, '괜찮아요', '가성비 좋은 상품입니다.', SYSDATE, 'review5.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (6, '만족스러워요', '사이즈도 딱 맞고 만족합니다.', SYSDATE, 'review6.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (7, '좋아요', '마음에 드는 제품이네요.', SYSDATE, 'review7.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (8, '만족합니다', '예상보다 품질이 좋아서 기쁩니다.', SYSDATE, 'review8.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (9, '추천해요', '친구에게도 추천할 만한 제품입니다.', SYSDATE, 'review9.png', 'user1', 1);
INSERT INTO tbl_review (r_idx, r_title, r_content, r_date, r_sfile, r_mid, r_pidx) 
VALUES (10, '만족합니다', '다음에도 재구매 의향이 있어요.', SYSDATE, 'review10.png', 'user1', 1);

-- 위시 테이블 더미 데이터
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (1, 1, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (2, 2, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (3, 3, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (4, 4, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (5, 5, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (6, 6, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (7, 7, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (8, 8, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (9, 9, 'user1');
INSERT INTO tbl_wish (w_idx, w_pidx, w_mid) VALUES (10, 10, 'user1');



