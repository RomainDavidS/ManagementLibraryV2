PGDMP         9        	         x         
   books_test    11.2    12.0 5    	           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            	           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            	           1262    72019 
   books_test    DATABASE     �   CREATE DATABASE books_test WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';
    DROP DATABASE books_test;
             	   adm_books    false            �            1259    98182    author    TABLE     �   CREATE TABLE public.author (
    id bigint NOT NULL,
    first_name character varying(255),
    last_name character varying(255)
);
    DROP TABLE public.author;
       public         	   adm_books    false            �            1259    97382    batch_job_execution    TABLE     �  CREATE TABLE public.batch_job_execution (
    job_execution_id bigint NOT NULL,
    version bigint,
    job_instance_id bigint NOT NULL,
    create_time timestamp without time zone NOT NULL,
    start_time timestamp without time zone,
    end_time timestamp without time zone,
    status character varying(10),
    exit_code character varying(2500),
    exit_message character varying(2500),
    last_updated timestamp without time zone,
    job_configuration_location character varying(2500)
);
 '   DROP TABLE public.batch_job_execution;
       public         	   adm_books    false            �            1259    97429    batch_job_execution_context    TABLE     �   CREATE TABLE public.batch_job_execution_context (
    job_execution_id bigint NOT NULL,
    short_context character varying(2500) NOT NULL,
    serialized_context text
);
 /   DROP TABLE public.batch_job_execution_context;
       public         	   adm_books    false            �            1259    97395    batch_job_execution_params    TABLE     `  CREATE TABLE public.batch_job_execution_params (
    job_execution_id bigint NOT NULL,
    type_cd character varying(6) NOT NULL,
    key_name character varying(100) NOT NULL,
    string_val character varying(250),
    date_val timestamp without time zone,
    long_val bigint,
    double_val double precision,
    identifying character(1) NOT NULL
);
 .   DROP TABLE public.batch_job_execution_params;
       public         	   adm_books    false            �            1259    72187    batch_job_execution_seq    SEQUENCE     �   CREATE SEQUENCE public.batch_job_execution_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.batch_job_execution_seq;
       public       	   adm_books    false            �            1259    97375    batch_job_instance    TABLE     �   CREATE TABLE public.batch_job_instance (
    job_instance_id bigint NOT NULL,
    version bigint,
    job_name character varying(100) NOT NULL,
    job_key character varying(32) NOT NULL
);
 &   DROP TABLE public.batch_job_instance;
       public         	   adm_books    false            �            1259    72192    batch_job_seq    SEQUENCE     v   CREATE SEQUENCE public.batch_job_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.batch_job_seq;
       public       	   adm_books    false            �            1259    97403    batch_step_execution    TABLE     �  CREATE TABLE public.batch_step_execution (
    step_execution_id bigint NOT NULL,
    version bigint NOT NULL,
    step_name character varying(100) NOT NULL,
    job_execution_id bigint NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone,
    status character varying(10),
    commit_count bigint,
    read_count bigint,
    filter_count bigint,
    write_count bigint,
    read_skip_count bigint,
    write_skip_count bigint,
    process_skip_count bigint,
    rollback_count bigint,
    exit_code character varying(2500),
    exit_message character varying(2500),
    last_updated timestamp without time zone
);
 (   DROP TABLE public.batch_step_execution;
       public         	   adm_books    false            �            1259    97416    batch_step_execution_context    TABLE     �   CREATE TABLE public.batch_step_execution_context (
    step_execution_id bigint NOT NULL,
    short_context character varying(2500) NOT NULL,
    serialized_context text
);
 0   DROP TABLE public.batch_step_execution_context;
       public         	   adm_books    false            �            1259    72206    batch_step_execution_seq    SEQUENCE     �   CREATE SEQUENCE public.batch_step_execution_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.batch_step_execution_seq;
       public       	   adm_books    false            �            1259    98190    books    TABLE     n  CREATE TABLE public.books (
    id bigint NOT NULL,
    availability bigint,
    id_cover character varying(255),
    isbn character varying(255),
    review bigint,
    summary text,
    title character varying(255),
    id_author bigint,
    id_book_reservation bigint,
    id_coauthor bigint,
    id_edition bigint,
    id_language bigint,
    id_theme bigint
);
    DROP TABLE public.books;
       public         	   adm_books    false            �            1259    98198    books_reservation    TABLE     �   CREATE TABLE public.books_reservation (
    id bigint NOT NULL,
    next_return_date timestamp without time zone,
    number integer,
    possible integer
);
 %   DROP TABLE public.books_reservation;
       public         	   adm_books    false            �            1259    98203    edition    TABLE     Y   CREATE TABLE public.edition (
    id bigint NOT NULL,
    name character varying(255)
);
    DROP TABLE public.edition;
       public         	   adm_books    false            �            1259    98208    email    TABLE     {   CREATE TABLE public.email (
    id bigint NOT NULL,
    content text,
    name character varying(255),
    subject text
);
    DROP TABLE public.email;
       public         	   adm_books    false            �            1259    98180    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public       	   adm_books    false            �            1259    98216    language    TABLE     [   CREATE TABLE public.language (
    id bigint NOT NULL,
    value character varying(255)
);
    DROP TABLE public.language;
       public         	   adm_books    false            �            1259    98221    lending    TABLE     �   CREATE TABLE public.lending (
    id bigint NOT NULL,
    end_date timestamp without time zone,
    id_user bigint,
    renewal integer,
    return_date timestamp without time zone,
    start_date timestamp without time zone,
    id_books bigint
);
    DROP TABLE public.lending;
       public         	   adm_books    false            �            1259    98226    reservation    TABLE     �   CREATE TABLE public.reservation (
    id bigint NOT NULL,
    id_user_reservation bigint,
    notification_date timestamp without time zone,
    reservation_date timestamp without time zone,
    state character varying(255),
    id_books bigint
);
    DROP TABLE public.reservation;
       public         	   adm_books    false            �            1259    98231    theme    TABLE     X   CREATE TABLE public.theme (
    id bigint NOT NULL,
    value character varying(255)
);
    DROP TABLE public.theme;
       public         	   adm_books    false            I           2606    98189    author author_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.author DROP CONSTRAINT author_pkey;
       public         	   adm_books    false    206            G           2606    97436 <   batch_job_execution_context batch_job_execution_context_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.batch_job_execution_context
    ADD CONSTRAINT batch_job_execution_context_pkey PRIMARY KEY (job_execution_id);
 f   ALTER TABLE ONLY public.batch_job_execution_context DROP CONSTRAINT batch_job_execution_context_pkey;
       public         	   adm_books    false    204            A           2606    97389 ,   batch_job_execution batch_job_execution_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.batch_job_execution
    ADD CONSTRAINT batch_job_execution_pkey PRIMARY KEY (job_execution_id);
 V   ALTER TABLE ONLY public.batch_job_execution DROP CONSTRAINT batch_job_execution_pkey;
       public         	   adm_books    false    200            =           2606    97379 *   batch_job_instance batch_job_instance_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.batch_job_instance
    ADD CONSTRAINT batch_job_instance_pkey PRIMARY KEY (job_instance_id);
 T   ALTER TABLE ONLY public.batch_job_instance DROP CONSTRAINT batch_job_instance_pkey;
       public         	   adm_books    false    199            E           2606    97423 >   batch_step_execution_context batch_step_execution_context_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.batch_step_execution_context
    ADD CONSTRAINT batch_step_execution_context_pkey PRIMARY KEY (step_execution_id);
 h   ALTER TABLE ONLY public.batch_step_execution_context DROP CONSTRAINT batch_step_execution_context_pkey;
       public         	   adm_books    false    203            C           2606    97410 .   batch_step_execution batch_step_execution_pkey 
   CONSTRAINT     {   ALTER TABLE ONLY public.batch_step_execution
    ADD CONSTRAINT batch_step_execution_pkey PRIMARY KEY (step_execution_id);
 X   ALTER TABLE ONLY public.batch_step_execution DROP CONSTRAINT batch_step_execution_pkey;
       public         	   adm_books    false    202            K           2606    98197    books books_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.books DROP CONSTRAINT books_pkey;
       public         	   adm_books    false    207            Q           2606    98202 (   books_reservation books_reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.books_reservation
    ADD CONSTRAINT books_reservation_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.books_reservation DROP CONSTRAINT books_reservation_pkey;
       public         	   adm_books    false    208            S           2606    98207    edition edition_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.edition
    ADD CONSTRAINT edition_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.edition DROP CONSTRAINT edition_pkey;
       public         	   adm_books    false    209            U           2606    98215    email email_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.email
    ADD CONSTRAINT email_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.email DROP CONSTRAINT email_pkey;
       public         	   adm_books    false    210            ?           2606    97381    batch_job_instance job_inst_un 
   CONSTRAINT     f   ALTER TABLE ONLY public.batch_job_instance
    ADD CONSTRAINT job_inst_un UNIQUE (job_name, job_key);
 H   ALTER TABLE ONLY public.batch_job_instance DROP CONSTRAINT job_inst_un;
       public         	   adm_books    false    199    199            W           2606    98220    language language_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.language DROP CONSTRAINT language_pkey;
       public         	   adm_books    false    211            Y           2606    98225    lending lending_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.lending
    ADD CONSTRAINT lending_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.lending DROP CONSTRAINT lending_pkey;
       public         	   adm_books    false    212            [           2606    98230    reservation reservation_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public         	   adm_books    false    213            ]           2606    98235    theme theme_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.theme
    ADD CONSTRAINT theme_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.theme DROP CONSTRAINT theme_pkey;
       public         	   adm_books    false    214            M           2606    98239 "   books uk_mvue38r6mcivkl1v9h996oe93 
   CONSTRAINT     l   ALTER TABLE ONLY public.books
    ADD CONSTRAINT uk_mvue38r6mcivkl1v9h996oe93 UNIQUE (id_book_reservation);
 L   ALTER TABLE ONLY public.books DROP CONSTRAINT uk_mvue38r6mcivkl1v9h996oe93;
       public         	   adm_books    false    207            O           2606    98237 !   books ukkibbepcitr0a3cpk3rfr7nihn 
   CONSTRAINT     \   ALTER TABLE ONLY public.books
    ADD CONSTRAINT ukkibbepcitr0a3cpk3rfr7nihn UNIQUE (isbn);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT ukkibbepcitr0a3cpk3rfr7nihn;
       public         	   adm_books    false    207            j           2606    98275 '   reservation fk3c0tihspsp9sngurcqusjn5ju    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk3c0tihspsp9sngurcqusjn5ju FOREIGN KEY (id_books) REFERENCES public.books(id);
 Q   ALTER TABLE ONLY public.reservation DROP CONSTRAINT fk3c0tihspsp9sngurcqusjn5ju;
       public       	   adm_books    false    2123    213    207            h           2606    98265 !   books fk481t17jmm4bgkhw84n48i4p7w    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fk481t17jmm4bgkhw84n48i4p7w FOREIGN KEY (id_theme) REFERENCES public.theme(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fk481t17jmm4bgkhw84n48i4p7w;
       public       	   adm_books    false    2141    207    214            g           2606    98260 !   books fkhkg2uiawf8ai3ogmxtillca7h    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fkhkg2uiawf8ai3ogmxtillca7h FOREIGN KEY (id_language) REFERENCES public.language(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fkhkg2uiawf8ai3ogmxtillca7h;
       public       	   adm_books    false    211    207    2135            d           2606    98245 !   books fklb20w7c7q1npg6hsmndmqc3ml    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fklb20w7c7q1npg6hsmndmqc3ml FOREIGN KEY (id_book_reservation) REFERENCES public.books_reservation(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fklb20w7c7q1npg6hsmndmqc3ml;
       public       	   adm_books    false    208    2129    207            f           2606    98255 !   books fko7oltwfh50rn4ngsmn8mfw36y    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fko7oltwfh50rn4ngsmn8mfw36y FOREIGN KEY (id_edition) REFERENCES public.edition(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fko7oltwfh50rn4ngsmn8mfw36y;
       public       	   adm_books    false    2131    207    209            i           2606    98270 #   lending fkpl5ypxlqulx3pmgev3bvorsji    FK CONSTRAINT     �   ALTER TABLE ONLY public.lending
    ADD CONSTRAINT fkpl5ypxlqulx3pmgev3bvorsji FOREIGN KEY (id_books) REFERENCES public.books(id);
 M   ALTER TABLE ONLY public.lending DROP CONSTRAINT fkpl5ypxlqulx3pmgev3bvorsji;
       public       	   adm_books    false    212    2123    207            e           2606    98250 !   books fksk5xcgnv4xsx0nh35e2cvcct9    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fksk5xcgnv4xsx0nh35e2cvcct9 FOREIGN KEY (id_coauthor) REFERENCES public.author(id);
 K   ALTER TABLE ONLY public.books DROP CONSTRAINT fksk5xcgnv4xsx0nh35e2cvcct9;
       public       	   adm_books    false    206    2121    207            c           2606    98240     books fkyiofkf1vw9o8fjf6byd58sps    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT fkyiofkf1vw9o8fjf6byd58sps FOREIGN KEY (id_author) REFERENCES public.author(id);
 J   ALTER TABLE ONLY public.books DROP CONSTRAINT fkyiofkf1vw9o8fjf6byd58sps;
       public       	   adm_books    false    2121    207    206            b           2606    97437 +   batch_job_execution_context job_exec_ctx_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.batch_job_execution_context
    ADD CONSTRAINT job_exec_ctx_fk FOREIGN KEY (job_execution_id) REFERENCES public.batch_job_execution(job_execution_id);
 U   ALTER TABLE ONLY public.batch_job_execution_context DROP CONSTRAINT job_exec_ctx_fk;
       public       	   adm_books    false    2113    204    200            _           2606    97398 -   batch_job_execution_params job_exec_params_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.batch_job_execution_params
    ADD CONSTRAINT job_exec_params_fk FOREIGN KEY (job_execution_id) REFERENCES public.batch_job_execution(job_execution_id);
 W   ALTER TABLE ONLY public.batch_job_execution_params DROP CONSTRAINT job_exec_params_fk;
       public       	   adm_books    false    201    200    2113            `           2606    97411 %   batch_step_execution job_exec_step_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.batch_step_execution
    ADD CONSTRAINT job_exec_step_fk FOREIGN KEY (job_execution_id) REFERENCES public.batch_job_execution(job_execution_id);
 O   ALTER TABLE ONLY public.batch_step_execution DROP CONSTRAINT job_exec_step_fk;
       public       	   adm_books    false    200    2113    202            ^           2606    97390 $   batch_job_execution job_inst_exec_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.batch_job_execution
    ADD CONSTRAINT job_inst_exec_fk FOREIGN KEY (job_instance_id) REFERENCES public.batch_job_instance(job_instance_id);
 N   ALTER TABLE ONLY public.batch_job_execution DROP CONSTRAINT job_inst_exec_fk;
       public       	   adm_books    false    2109    199    200            a           2606    97424 -   batch_step_execution_context step_exec_ctx_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.batch_step_execution_context
    ADD CONSTRAINT step_exec_ctx_fk FOREIGN KEY (step_execution_id) REFERENCES public.batch_step_execution(step_execution_id);
 W   ALTER TABLE ONLY public.batch_step_execution_context DROP CONSTRAINT step_exec_ctx_fk;
       public       	   adm_books    false    2115    203    202           