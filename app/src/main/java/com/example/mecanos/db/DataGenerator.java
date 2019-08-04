package com.example.mecanos.db;

import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.db.entity.PlatoIngredientEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataGenerator {
    private static final String[] PLATOS = new String[]{
            "Sanduche", "Empanada", "Papas Fritas", "Jugos", "Gaseosas"};
    private static final String[] DESCRIPTION = new String[]{
            "Deliciosos", "picante",  "Rick and Morty", "is \uD83D\uDCAF", "is ❤️", "is fine"};
    private static final String[] PROVEEDORES = new String[]{
            "La esquina", "Artesanos del Pan", "Galpon de Oro", "Monocle"};
    private static final String[] INGREDIENTS = new String[]{
            "Pan", "Tomate", "Salchicha", "Carne", "Chicharron", "Salsas"};
    private static final String[] UNIDADES = new String[]{
            "grms.", "lts", "unds","pqts"};

    public static List<PlatoEntity> generatePlatos() {
        List<PlatoEntity> platos = new ArrayList<>(PLATOS.length );
        Random rnd = new Random();
        for (int i = 0; i < PLATOS.length; i++) {
                PlatoEntity plato = new PlatoEntity();
                plato.setId(i);
                plato.setName(PLATOS[i]);
                plato.setDescription(plato.getName() + " " + DESCRIPTION[i]);
                plato.setPrice(rnd.nextFloat());
                plato.setCosto(rnd.nextFloat());
                platos.add(plato);
        }
        return platos;
    }

    public static List<IngredientEntity> generateIngredients() {
        List<IngredientEntity> ingredients = new ArrayList<>();
        Random rnd = new Random();

            int ingredientsNumber = rnd.nextInt(5) + 1;
            for (int i = 0; i < INGREDIENTS.length; i++) {
                IngredientEntity ingredient = new IngredientEntity();
                ingredient.setId(i);
                ingredient.setNombre(INGREDIENTS[i]);
                ingredient.setProveedor(PROVEEDORES[rnd.nextInt(PROVEEDORES.length)]);
                ingredient.setUnidades(UNIDADES[rnd.nextInt(UNIDADES.length)]);
                ingredient.setDiaCompra(new Date(System.currentTimeMillis()
                        - TimeUnit.DAYS.toMillis(ingredientsNumber - i) + TimeUnit.HOURS.toMillis(i)));
                ingredients.add(ingredient);
            }

        return ingredients;
    }

    public static List<PlatoIngredientEntity> generateIngredientsForPlatos() {
        List<PlatoIngredientEntity> platoIngredients = new ArrayList<>();
        Random rnd = new Random();
        int index = 0;
        for (int i = 0; i<PLATOS.length; i++){
            int ingredientsNumber = rnd.nextInt(INGREDIENTS.length-1)+1;
            for (int j = 0; j <ingredientsNumber; j++){
                PlatoIngredientEntity pi = new PlatoIngredientEntity();
                pi.setId(index);
                pi.setPlatoId(i);
                pi.setIngredientId(rnd.nextInt(INGREDIENTS.length));
                pi.setCantidad(rnd.nextFloat());
                platoIngredients.add(pi);
                index++;
            }
        }
        return platoIngredients;
    }
}
