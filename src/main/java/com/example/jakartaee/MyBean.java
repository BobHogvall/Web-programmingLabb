package com.example.jakartaee;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class MyBean {
}

//@ApplicationScoped => finns bara ett objekt av den, denna kommer användas för alla
//@SessionScoped => skapas ett exemplar per anslutning.