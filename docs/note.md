CREATE TABLE Account_Club (
account_club_id INT AUTO_INCREMENT PRIMARY KEY,
account_id INT NOT NULL,
club_id INT NOT NULL,
is_deleted BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (account_id) REFERENCES Account(account_id),
    FOREIGN KEY (club_id) REFERENCES Club(club_id),

    UNIQUE (account_id, club_id)
);