DROP TABLE users, phones, cascade;

CREATE TABLE users (
   id UUID PRIMARY KEY DEFAULT RANDOM_UUID(),
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   last_login TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   token VARCHAR(255),
   is_active BOOLEAN NOT NULL DEFAULT TRUE,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE phones (
    id SERIAL PRIMARY KEY,
    number VARCHAR(15) NOT NULL,
    city_code VARCHAR(5) NOT NULL,
    country_code VARCHAR(5) NOT NULL,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_phones_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_users_email ON users(email);

