-- 插入一些初始数据

-- 用户数据
INSERT INTO users (username, password, email) VALUES
                                                  ('admin', 'admin_password', 'admin@example.com'),
                                                  ('user1', 'user1_password', 'user1@example.com'),
                                                  ('user2', 'user2_password', 'user2@example.com');

-- 问题数据
INSERT INTO issues (title, description, status, priority, type, custom_fields) VALUES
                                                                                   ('Sample Issue 1', 'This is a sample issue 1', 'new', 'high', 'bug', '{"field1": "value1", "field2": "value2"}'),
                                                                                   ('Sample Issue 2', 'This is a sample issue 2', 'in_progress', 'medium', 'task', '{"field1": "value1", "field2": "value2"}');

-- 评论数据
INSERT INTO comments (issue_id, content) VALUES
                                             (1, 'This is a comment for issue 1'),
                                             (1, 'Another comment for issue 1'),
                                             (2, 'This is a comment for issue 2');

-- 甘特图任务数据
INSERT INTO gantt_tasks (name, start, end) VALUES
                                               ('Task 1', '2024-06-01', '2024-06-07'),
                                               ('Task 2', '2024-06-08', '2024-06-14');

-- Wiki页面数据
INSERT INTO wiki_pages (title, content) VALUES
                                            ('Home', 'Welcome to the Wiki'),
                                            ('Setup', 'Setup instructions');

-- 提交记录数据
INSERT INTO commits (id, message, author, date) VALUES
                                                    ('abc123', 'Initial commit', 'user1', '2024-06-01T12:34:56'),
                                                    ('def456', 'Added new feature', 'user2', '2024-06-02T14:22:33');

-- 报告数据
INSERT INTO reports (issue, status, task, completion) VALUES
                                                          ('Sample Issue 1', 'new', 'Task 1', 75),
                                                          ('Sample Issue 2', 'in_progress', 'Task 2', 50);

-- 查询数据
INSERT INTO queries (name, filters) VALUES
                                        ('Open Issues', '{"status":"open"}'),
                                        ('High Priority Issues', '{"priority":"high"}');

-- 时间日志数据
INSERT INTO timelogs (description, hours) VALUES
                                              ('Worked on bug fix', 3),
                                              ('Worked on new feature', 5);

-- 通知数据
INSERT INTO notifications (message, date) VALUES
                                              ('New issue assigned to you', '2024-06-01T12:34:56'),
                                              ('Your issue has been updated', '2024-06-02T14:22:33');

-- 登录历史数据
INSERT INTO login_history (user_id, date, ip) VALUES
                                                  (1, '2024-06-01T12:34:56', '192.168.0.1'),
                                                  (2, '2024-06-02T14:22:33', '192.168.0.2');