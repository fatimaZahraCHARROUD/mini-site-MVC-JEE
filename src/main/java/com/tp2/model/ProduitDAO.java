package com.tp2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProduitDAO {

     private final Map<Long, Produit> produits = new ConcurrentHashMap<>();

    private final AtomicLong sequence = new AtomicLong(0);

     public ProduitDAO() {
        save(new Produit(null, "Laptop", "Dell XPS 13", new BigDecimal("9500.00"), 5));
        save(new Produit(null, "Souris", "Logitech MX Master", new BigDecimal("650.00"), 20));
    }

     public List<Produit> findAll() {
        List<Produit> list = new ArrayList<>(produits.values());//key value
        list.sort(Comparator.comparing(Produit::getId)); // Trie par ID croissant
        return list;
    }

     public Produit findById(Long id) {
        if (id == null) return null;
        return produits.get(id);
    }

     public Long save(Produit p) {
        if (p.getId() == null) {
             p.setId(sequence.incrementAndGet());
        }
         produits.put(p.getId(), p);//k,v
        return p.getId();
    }

     public void delete(Long id) {
        if (id != null) {
            produits.remove(id);
        }
    }
}