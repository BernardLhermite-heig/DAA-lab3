# Développement Android

## Laboratoire n°3: Architecture MVVM, utilisation d’une base de données Room et d’un RecyclerView

### Friedli Jonathan, Marengo Stéphane, Silvestri Géraud

### 15.11.2022

## Introduction
Le but de ce laboratoire est de développer une application android basée sur une architechture `MVVM` et ayant une base de données `Room`. Cette application permet de gérer une liste de tâches à faire. De plus, ladite application doit avoir une interface graphique différentes pour les smartphones et les tablettes.

Version téléphone:

![Version Mobile](/images/mobileVersiond.jpg) 

Version tablette:

![Version Tablette](/images/tabletVersiond.png)
//TODO Mettre l'image tablette quand les notes sont fix.
## 1. Détails d'implémentation

### 1.1. Layout
Puisque l'inferface doit être différente entre la version téléphone et la version tablettte, nous avons créé un dossier `layout` et un autre dossier `layout-sw600dp`. Les deux dossiers contiennent un fichier `activity_main.xml` contenant le layout de l'application. De cette manière, le layout de l'application est différent selon la taille de l'appareil. A partir de 600dp, l'application est considérée comme une tablette.

Layout téléphone:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginStart="4dp"
    android:layout_marginEnd="8dp"
    tools:context=".activities.MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/notes_fragment"
        android:name="ch.heigvd.daa_lab3.fragments.NotesFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Layout tablette:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginStart="4dp"
    android:layout_marginEnd="8dp"
    tools:context=".activities.MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/notes_fragment"
        android:name="ch.heigvd.daa_lab3.fragments.NotesFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Pour les menus, nous avons fait de la même manière avec un dossier `menu` et un autre dossier `menu-sw600dp` et les deux dossiers contiennent un fichier `main_menu.xml`.

## 2. Question complémentaire

### 2.1
*Quelle est la meilleure approche pour sauver le choix de l'option de tri de la liste des notes ? Vous justifierez votre réponse et l’illustrez en présentant le code mettant en œuvre votre approche.*

### 2.2
*L'accès à la liste des notes issues de la base de données `Room` se fait avec une `LiveData`. Est ce que cette solution présente des limites ? Si oui, quelles sont-elles ? Voyez-vous une autre approche plus adaptée ?*

### 2.3
*Les notes affichées dans la `RecyclerView` ne sont pas sélectionnables ni cliquables. Comment procéderiez-vous si vous souhaitiez proposer une interface permettant de sélectionner une note pour l’éditer ?*

La `RecyclerView` n'a pas de `Item Click Listener`, il faudrait donc que nous l'implémentions nous-même. La classe MainActivity devrait donc implémenter une interface pour les `onClick event`. Cette interface serait ensuite passée à la `RecylcerView Adapter`, puis la `ViewHolder` appelerait la méthode de l'interface qui donnerait la position de l'item cliqué.