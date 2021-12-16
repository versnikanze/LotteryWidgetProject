BEGIN;


CREATE TABLE IF NOT EXISTS public.contestants
(
    idcontestants serial NOT NULL,
    contestant_name character varying(45) NOT NULL,
    picked_number integer NOT NULL,
    PRIMARY KEY (idcontestants)
);

CREATE TABLE IF NOT EXISTS public.raffles
(
    raffle_id serial NOT NULL,
    created_at character varying(30) NOT NULL,
    lottery_number integer NOT NULL,
    PRIMARY KEY (raffle_id)
);

CREATE TABLE IF NOT EXISTS public.winners
(
    winner_id serial NOT NULL,
    contestant_name character varying(45) NOT NULL,
    raffle_id serial NOT NULL,
    PRIMARY KEY (winner_id)
);

ALTER TABLE IF EXISTS public.winners
    ADD FOREIGN KEY (raffle_id)
    REFERENCES public.raffles (raffle_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

END;

begin;

create or replace function public.winners_change_notify ()
 returns trigger
 language plpgsql
as $$
declare
  channel text := TG_ARGV[0];
begin
  PERFORM (
     with payload(winner_id, contestant_name, raffle_id) as
     (
       select NEW.winner_id, NEW.contestant_name, NEW.raffle_id
     )
     select pg_notify(channel, row_to_json(payload)::text)
       from payload
  );
  RETURN NULL;
end;
$$;

CREATE TRIGGER notify_counters
         AFTER INSERT
            ON winners
      FOR EACH ROW
       EXECUTE PROCEDURE public.tg_notify_counters('winners.notify');

commit;