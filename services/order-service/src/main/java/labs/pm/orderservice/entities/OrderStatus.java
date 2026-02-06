package labs.pm.orderservice.entities;

public enum OrderStatus {
    PENDING,    // Commande créée, en attente de validation ou paiement
    CONFIRMED,  // Validée (ex: restaurant accepte)
    READY,      // Prête à être récupérée
    DELIVERED,  // Livrée au client
    CANCELLED   // Annulée
}

