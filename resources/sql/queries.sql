-- name: db-query-next-id
-- Get next ID from ids seq
select id_seq.nextval as id from dual

-- name: db-insert-message!
-- Put message to DB
insert into messages (id, ts, message) values (:id, :ts, :message)

-- name: db-find-messages
-- Get messages
select id, ts, message from messages

-- name: db-find-messages-since
-- Get messages
select id, ts, message from messages where ts > :since
