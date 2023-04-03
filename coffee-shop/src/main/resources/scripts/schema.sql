-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id uuid NOT NULL,
    status character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    origin_name character varying(255) NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: origin_coffee_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.origin_coffee_types (
    origin_name character varying(255) NOT NULL,
    coffee_type character varying(255) NOT NULL
);


ALTER TABLE public.origin_coffee_types OWNER TO postgres;

--
-- Name: origins; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.origins (
    name character varying(255) NOT NULL
);


ALTER TABLE public.origins OWNER TO postgres;

--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: origin_coffee_types origin_coffee_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.origin_coffee_types
    ADD CONSTRAINT origin_coffee_types_pkey PRIMARY KEY (origin_name, coffee_type);


--
-- Name: origins origins_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.origins
    ADD CONSTRAINT origins_pkey PRIMARY KEY (name);


--
-- Name: origin_coffee_types fksny5vf8j30otg213opmbp0ab9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.origin_coffee_types
    ADD CONSTRAINT fksny5vf8j30otg213opmbp0ab9 FOREIGN KEY (origin_name) REFERENCES public.origins(name);


--
-- Name: orders fkt474abpx5pxojjm61tb1d64vp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fkt474abpx5pxojjm61tb1d64vp FOREIGN KEY (origin_name) REFERENCES public.origins(name);


--
-- PostgreSQL database dump complete
--

