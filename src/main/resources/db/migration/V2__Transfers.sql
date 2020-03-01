SET search_path=public;

CREATE TABLE IF NOT EXISTS transfers
(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    source_account_id  uuid NOT NULL REFERENCES accounts(id),
    destination_account_id uuid NOT NULL REFERENCES accounts(id),
    amount int NOT NULL DEFAULT 0 CHECK (amount > 0),
    transfer_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO transfers (id, source_account_id, destination_account_id, amount) VALUES
     ('45803ac4-d9a2-44a7-ac88-ed95b51b6ec0', '177e41d3-cb1b-4426-aaea-3d3d5623ac6c', 'e2a77256-09a4-4910-b646-d27614829abd', 200),
     ('19761b85-492a-404c-987c-29bb8349dca3','a933d974-089e-46ef-ab72-91897b47977b', '177e41d3-cb1b-4426-aaea-3d3d5623ac6c', 40);                                                                          ;
