
-- 1. 사용자 먼저 삽입
INSERT IGNORE INTO user_account (created_at, modified_at, user_id, created_by, email, modified_by, username, user_password)
VALUES (now(), now(), 'yunkyeong', 'yunkyeong', 'yun@gmail.com', 'yunkyeong', 'yunkyeong', '1234');

-- 2. 삽입 확인
SELECT user_id FROM user_account WHERE user_id = 'yunkyeong';

-- 3. 문제없으면 article 삽입
INSERT INTO article (title, content, password, author, user_account_user_id, created_by, created_at, modified_by, modified_at)
VALUES ('Proin leo odio, porttitor id, consequat in, consequat ut, nulla.',
        'Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.',
        'dH0hBTstvXa', 'lmarlowe0', 'yunkyeong', 'yunkyeong', '2022-09-26 09:48:42', 'Lorens', '2023-11-27 10:30:37');
