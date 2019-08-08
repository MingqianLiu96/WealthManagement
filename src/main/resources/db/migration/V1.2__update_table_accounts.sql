ALTER TABLE account
RENAME TO accounts;

ALTER TABLE accounts
RENAME COLUMN users_id TO user_id;