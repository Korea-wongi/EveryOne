-- 멤버 테이블
CREATE TABLE tbl_member(
    m_id VARCHAR2(100) PRIMARY KEY NOT NULL, -- 멤버 아이디
    m_pass VARCHAR2(100),                    -- 멤버 비밀번호
    m_name VARCHAR2(50) NOT NULL,            -- 멤버 이름
    m_email VARCHAR2(100) NOT NULL,          -- 멤버 이메일
    m_hp VARCHAR2(50),                       -- 멤버 전화번호
    m_birth DATE,                            -- 멤버 생년월일
    m_gender VARCHAR2(20),                   -- 멤버 성별
    m_address1 VARCHAR2(500),                -- 멤버 필수주소
    m_regdate DATE,                          -- 멤버 가입일자
    m_admin VARCHAR2(50)                     -- 멤버 관리,회원
);

    
-- 상품 테이블
CREATE TABLE tbl_product(
    p_idx NUMBER PRIMARY KEY NOT NULL,       -- 상품 인덱스
    p_mid VARCHAR2(100) NOT NULL,            -- 회원 아이디
    p_category VARCHAR2(50) NOT NULL,        -- 상품 분류
    p_name VARCHAR2(100) NOT NULL,           -- 상품 이름
    p_price NUMBER NOT NULL,                 -- 상품 가격
    p_color VARCHAR2(100) NOT NULL,          -- 상품 색상
    p_size VARCHAR2(100) NOT NULL,           -- 상품 사이즈
    p_content VARCHAR2(2000) NOT NULL,       -- 상품 설명
    p_codytemp NUMBER,
    CONSTRAINT tbl_product_p_mid FOREIGN KEY (p_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE
);



-- 상품 테이블 시퀀스
CREATE SEQUENCE tbl_product_p_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;
    
-- 상품 이미지 테이블
CREATE TABLE tbl_img(
    i_pidx NUMBER NOT NULL,              -- 상품 번호참조
    i_name VARCHAR2(100) NOT NULL,       -- 상품 이미지 이름
    i_path VARCHAR2(2000) NOT NULL,      -- 상품 이미지 경로
    CONSTRAINT tbl_img_i_pidx FOREIGN KEY (i_pidx)
    REFERENCES tbl_product(p_idx) ON DELETE CASCADE
);


-- 장바구니 테이블
CREATE TABLE tbl_cart(
    c_idx NUMBER PRIMARY KEY NOT NULL,   -- 카트 인덱스
    c_pidx NUMBER NOT NULL,              -- 상품 번호
    c_mid VARCHAR2(100) NOT NULL,        -- 멤버 아이디
    c_name VARCHAR2(100) NOT NULL,       -- 상품 이름
    c_amount NUMBER NOT NULL,            -- 상품 구매 수량
    c_price NUMBER NOT NULL,             -- 상품 가격
    CONSTRAINT tbl_cart_c_pidx FOREIGN KEY (c_pidx)
    REFERENCES tbl_product(p_idx) ON DELETE CASCADE,
    CONSTRAINT tbl_cart_c_mid FOREIGN KEY (c_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE
);


-- 장바구니 테이블 시퀀스
CREATE SEQUENCE tbl_cart_c_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;

-- 결제 테이블
CREATE TABLE tbl_pay(
    p_idx VARCHAR2(100) PRIMARY KEY NOT NULL,    -- 결제 인덱스
    p_mid VARCHAR2(100) NOT NULL,                -- 멤버 아이디
    p_price NUMBER NOT NULL,                     -- 상품 가격
    p_date DATE NOT NULL,                        -- 결제 날짜
    CONSTRAINT tbl_pay_p_mid FOREIGN KEY (p_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE
);


-- 결제 테이블 시퀀스
CREATE SEQUENCE tbl_pay_p_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;

-- 주문 테이블
CREATE TABLE tbl_order(
    o_idx VARCHAR2(100) PRIMARY KEY NOT NULL, -- 주문 인덱스
    o_mid VARCHAR2(100) NOT NULL,             -- 멤버 아이디
    o_name VARCHAR2(50) NOT NULL,             -- 배송 수령자
    o_address1 VARCHAR2(500) NOT NULL,        -- 배송 필수주소
    o_hp VARCHAR2(50) NOT NULL,               -- 배송 수령인 전화번호
    o_request VARCHAR2(500),                  -- 배송 요청사항
    CONSTRAINT tbl_order_o_mid FOREIGN KEY (o_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE
);




-- 주문 테이블 시퀀스
CREATE SEQUENCE tbl_order_o_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;

-- 구매 테이블
CREATE TABLE tbl_buy(
    b_idx NUMBER PRIMARY KEY NOT NULL,    -- 구매 인덱스
    b_oidx VARCHAR2(100) NOT NULL,        -- 주문 번호
    b_name VARCHAR2(100) NOT NULL,        -- 상품 이름
    b_price NUMBER NOT NULL,              -- 상품 가격
    b_pmid VARCHAR2(1000) NOT NULL,       -- 판매자 명
    b_date Date NOT NULL,
    CONSTRAINT tbl_buy_b_oidx FOREIGN KEY (b_oidx)
    REFERENCES tbl_order(o_idx) ON DELETE CASCADE
);


-- 구매 테이블 시퀀스
CREATE SEQUENCE tbl_buy_b_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;

-- 공지사항 테이블
CREATE TABLE tbl_news(
    n_idx NUMBER PRIMARY KEY NOT NULL,      -- 공지 인덱스
    n_title VARCHAR2(200) NOT NULL,         -- 공지 제목
    n_content VARCHAR2(2000) NOT NULL,      -- 공지 내용
    n_date DATE NOT NULL,                   -- 공지 날짜
    n_cnt NUMBER NOT NULL,                  -- 공지 조회수
    n_sfile VARCHAR2(200),                  -- 공지 파일
    n_mid VARCHAR2(100) NOT NULL,           -- 멤버 아이디
    CONSTRAINT tbl_news_n_mid FOREIGN KEY (n_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE
);

-- 공지사항 테이블 시퀀스
CREATE SEQUENCE tbl_news_n_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;

-- QnA 테이블
CREATE TABLE tbl_qna(
    q_idx NUMBER PRIMARY KEY NOT NULL,      -- qna 인덱스
    q_title VARCHAR2(200) NOT NULL,         -- qna 제목
    q_content VARCHAR2(2000) NOT NULL,      -- qna 내용
    q_group NUMBER NOT NULL,                -- qna 주글
    q_level NUMBER NOT NULL,                -- qna 들여쓰기
    q_date DATE NOT NULL,                   -- qna 날짜
    q_cnt NUMBER NOT NULL,                  -- qna 조회수
    q_sfile VARCHAR2(200),                  -- qna 파일
    q_mid VARCHAR2(100) NOT NULL,           -- 멤버 아이디
    q_bidx NUMBER NOT NULL,                 -- 구매 인덱스
    CONSTRAINT tbl_qna_q_mid FOREIGN KEY (q_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE,
    CONSTRAINT tbl_qna_q_bidx FOREIGN KEY (q_bidx)
    REFERENCES tbl_buy(b_idx) ON DELETE CASCADE
);

-- QnA 테이블 시퀀스
CREATE SEQUENCE tbl_qna_q_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;


-- 리뷰 테이블
CREATE TABLE tbl_review(
    r_idx NUMBER PRIMARY KEY NOT NULL,      -- 리뷰 인덱스
    r_title VARCHAR2(200) NOT NULL,         -- 리뷰 제목
    r_content VARCHAR2(2000) NOT NULL,      -- 리뷰 내용
    r_date DATE NOT NULL,                   -- 리뷰 날짜
    r_sfile VARCHAR2(200),                  -- 리뷰 파일
    r_mid VARCHAR2(100) NOT NULL,           -- 멤버 아이디
    r_pidx NUMBER NOT NULL,                 -- 상품 인덱스
    CONSTRAINT tbl_review_r_mid FOREIGN KEY (r_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE,
    CONSTRAINT tbl_review_r_pidx FOREIGN KEY (r_pidx)
    REFERENCES tbl_product(p_idx) ON DELETE CASCADE
);


-- 리뷰 테이블 시퀀스
CREATE SEQUENCE tbl_review_r_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;

-- 찜 테이블
CREATE TABLE tbl_wish(
    w_idx NUMBER NOT NULL PRIMARY KEY,     -- 찜 인덱스
    w_pidx NUMBER NOT NULL,                -- 상품 번호
    w_mid VARCHAR2(100) NOT NULL,          -- 멤버 아이디
    CONSTRAINT tbl_wish_w_mid FOREIGN KEY (w_mid)
    REFERENCES tbl_member(m_id) ON DELETE CASCADE,
    CONSTRAINT tbl_wish_w_pidx FOREIGN KEY (w_pidx)
    REFERENCES tbl_product(p_idx) ON DELETE CASCADE
);

-- 찜 시퀀스
CREATE SEQUENCE tbl_wish_w_idx
    INCREMENT BY 1
    START with 1
    MINVALUE 1
    MAXVALUE 9999
    NOCYCLE
    NOCACHE
    NOORDER;
    commit;
    