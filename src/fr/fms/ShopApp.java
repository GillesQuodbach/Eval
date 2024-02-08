package fr.fms;

import java.util.ArrayList;
import java.util.Scanner;
import fr.fms.authentication.Authenticate;
import fr.fms.business.IBusinessImpl;
import fr.fms.dao.ArticleDao;
import fr.fms.dao.DaoFactory;
import fr.fms.dao.UserDao;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;
import fr.fms.entities.Order;
import fr.fms.entities.User;

/**
 * Application console de vente de formation en base permettant d'exploiter une
 * couche métier/dao pour créer un panier en ajoutant ou retirant des articles
 * puis passer commande à tout instant, cela génère une commande en base avec
 * tous les éléments associés
 * 
 * @author QUODBACH Gilles - 2024
 * 
 */

public class ShopApp {
	private static Scanner scan = new Scanner(System.in);
	private static IBusinessImpl business = new IBusinessImpl();
	private static Authenticate authenticate = new Authenticate();
	public static final String TEXT_BLUE = "\u001B[36m";
	public static final String TEXT_GREEN = "\u001B[32m";
	public static final String TEXT_RESET = "\u001B[0m";
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_NAME = "NOM";
	private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
	private static final String COLUMN_DURATION = "DUREE/JOUR";
	private static final String COLUMN_FORMAT = "FORMAT";
	private static final String COLUMN_PRICE = "PRIX/€";
	private static final String COLUMN_ORDERID = "Id Cmde";
	private static final String COLUMN_ORDERDATE = "Date";
	private static final String COLUMN_ORDERCUSTOMERID = "Id Client";

	private static int idUser = 0;
	private static String userRole = null;
	private static String login = null;

	public static void main(String[] args) {
		
		boolean isAdminLoggedIn = false;
		
		System.out.println("Bonjour et bienvenu sur FMS FORMATION, voici la liste de nos formations \n");
		displayArticles();
		int choice = 0;
		while (choice != 20) {
			if (userRole != null) {
				isAdminLoggedIn = true;
			}
		if (isAdminLoggedIn) {
			displayAdminMenu() ;
		} else {
			 displayMenu();
		}
			choice = scanInt();
			switch (choice) {
			case 1:
//				System.out.println("1 : Ajouter une formation au panier");
				addArticle();
				break;
			case 2:
//				System.out.println("2 : Retirer une formation du panier");
				removeArticle();
				break;
			case 3:
//				System.out.println("3 : Afficher mon panier + total pour passer commande");
				displayCart(true);
				break;
			case 4:
//				System.out.println("4 : Afficher toutes les formations");
				displayArticles();
				break;
			case 5:
//				System.out.println("5 : Afficher les formations en distancielles");
				displayRemote();
				break;
			case 6:
//				System.out.println("6 : Afficher les formations en présentielles");
				displayPresent();
				break;
			case 7:
//				System.out.println("7 : Rechercher une formation par mot clé");
				displayArticlesByKeyWord();
				break;
			case 8:
//				System.out.println("8 : Afficher toutes les catégories en base");
				displayCategories();
				break;
			case 9:
//				System.out.println("9 : Afficher tous les formations d'une catégorie");
				displayArticlesByCategoryId();
				break;
			case 10:
//				System.out.println("10 : Connexion(Deconnexion) à votre compte");
				connection();
				break;
			case 11:
//				System.out.println("11 : Connexion(Deconnexion) compte ADMINISTRATION");
				try {
				adminConnection();
				}catch (Exception e) {
					System.out.println("Accès refusé");
				}
				break;
			case 12:
//				System.out.println("12 : Ajouter une formation à la boutique");
				displayArticles();
				addFormationToShop();
				break;
			case 13:
//				System.out.println("13 : Supprimer une formation de la boutique");
				displayArticles();
				deleteFormationFromShop();
				break;
			case 14:
//				System.out.println("14 : Modifier une formation de la boutique");
				displayArticles();
				upDateFormationFromShop();
				break;
			case 15:
//				System.out.println("15 : Ajouter une catégorie de formation");
				displayCategories();
				addCategoryToShop();
				
				break;
			case 16:
//				System.out.println("16 : Supprimer une catégorie de formation");
				displayCategories();
				deleCategoryFromShop();
				
				break;
			case 17:
//				System.out.println("17 : Modifier une catégorie de formation");
				displayCategories();
				upDateCategoryFromShop();
				break;
			case 18:
//				System.out.println("18 :Lire toutes les commandes");
				displayOrders();
				break;
			case 19:
//				System.out.println("18 :Lire les commandes d'un client");
				displayOrders();
				displayOrderByCustomerId();
		
				break;
			case 20:
				System.out.println("à bientôt dans notre boutique :)");
				break;
			default:
				System.out.println("veuillez saisir une valeur entre 1 et 12");
			}
		}
	}

	/**
	 * Méthode qui affiche le menu principale
	 */
	public static void displayMenu() {
		if (login != null)
			System.out.print(TEXT_BLUE + "Compte : " + login);
		System.out.println("\n" + "Pour réaliser une action, tapez le code correspondant");
		System.out.println("1 : Ajouter une formation au panier");
		System.out.println("2 : Retirer une formation du panier");
		System.out.println("3 : Afficher mon panier + total pour passer commande");
		System.out.println("4 : Afficher toutes les formations");
		System.out.println("5 : Afficher les formations en distancielles");
		System.out.println("6 : Afficher les formations en présentielles");
		System.out.println("7 : Rechercher une formation par mot clé");
		System.out.println("8 : Afficher toutes les catégories en base");
		System.out.println("9 : Afficher tous les formations d'une catégorie");
		System.out.println("10 : Connexion(Deconnexion) à votre compte");
		System.out.println("11 : Connexion(Deconnexion) compte ADMINISTRATION");
		System.out.println("20 : Sortir de l'application");
	}
	
	/**
	 * Méthode qui affiche le menu principale
	 */
	public static void displayAdminMenu() {
		if (login != null)
			
			System.out.println(TEXT_GREEN + "Compte ADMINISTRATEUR : " + login);
		System.out.println("Pour réaliser une action, tapez le code correspondant");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "1", "Ajouter une formation au panier", "12", "Ajouter une formation à la boutique");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "2", "Retirer une formation du panier", "13", "Supprimer une formation de la boutique");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "3", "Afficher mon panier + total", "14", "Modifier une formation de la boutique");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "4", "Afficher toutes les formations", "15", "Ajouter une catégorie de formation");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "5", "Afficher les formations en distancielles", "16", "Supprimer une catégorie de formation");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "6", "Afficher les formations en présentielles", "17", "Modifier une catégorie de formation");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "7", "Rechercher une formation par mot clé", "18", "Voir toutes les commandes");
		System.out.printf("%-2s : %-40s %-2s : %-40s%n", "8", "Afficher toutes les catégories en base", "19", "Voir commandes d'un client");
		System.out.printf("%-2s   %-40s %-2s : %-40s%n", "", "", "20", "Sortir de l'application");
	}

	/**
	 * Méthode qui modifie une categorie
	 */
	public static void upDateCategoryFromShop() {
		System.out.println("Saisissez l'ID de catégorie à modifier: ");
		int idCategoryToUpdate = scan.nextInt();
		scan.nextLine();
		System.out.println();
		System.out.println("Saisissez le nouveau nom de la catégorie: ");
		String categoryNameToUpDate = scan.nextLine();
		System.out.println("Saisissez la nouvelle descritpion de la catégorie: ");
		String categoryDescriptionToUpDate = scan.nextLine();
		DaoFactory.getCategoryDao().update(new Category(idCategoryToUpdate,categoryNameToUpDate, categoryDescriptionToUpDate));
	}
	
	/**
	 * Méthode qui supprime une categorie
	 */
	public static void deleCategoryFromShop() {
		System.out.println("Saisissez l'ID de la catégorie à supprimer: ");
		int categoryIdToRemove = scan.nextInt();
		scan.nextLine();
		Category categoryToRemove = DaoFactory.getCategoryDao().read(categoryIdToRemove);
		DaoFactory.getCategoryDao().delete(categoryToRemove);
	}
	/**
	 * Méthode qui ajoute une categorie
	 */
	public static void addCategoryToShop() {
		System.out.println("Saisissez le nom de la nouvelle catégorie: ");
		String newCategoryName = scan.next();
		System.out.println("Saisissez la description de la nouvelle catégorie: ");
		String newCategoryDescription = scan.next();
		DaoFactory.getCategoryDao().create(new Category(newCategoryName, newCategoryDescription));
	}
	
	/**
	 * Méthode qui modifie une formation
	 */
	public static void upDateFormationFromShop() {
		System.out.println("Saisissez l'ID de la formation à modifier: ");
		int articleIdToUpdate = scan.nextInt();
		scan.nextLine();
		System.out.println();
		System.out.println("Saisissez le nouveau nom de la formation: ");
		String articleNameToUpDate = scan.nextLine();
		System.out.println("Saisissez la nouvelle descritpion de la formation: ");
		String articleDescriptionToUpDate = scan.nextLine();
		System.out.println("Saisissez la nouvelle durée de la formation: ");
		int articleDurationToUpDate = scan.nextInt();
		scan.nextLine();
		System.out.println("Saisissez le nouveau format de la formation: ");
		System.out.println("1 : Présentielle 2: Distancielle ");

		String courseFormatToUpdate = "";
		int updatedCourseFormatChoice = scan.nextInt();
		scan.nextLine();
		switch (updatedCourseFormatChoice) {
		case 1: courseFormatToUpdate = "Présentielle";
		break;
		case 2 : courseFormatToUpdate = "Distancielle";
		}

		System.out.println("Quel est le prix de la nouvelle formation: ");
		double newUpdatedCoursePrice = scan.nextDouble();
		scan.nextLine();
		System.out.println("Quel est la catégorie de la nouvelle formation: ");
		int newUpdatedCourseCategory = scan.nextInt();
		scan.nextLine();
		DaoFactory.getArticleDao().update(new Article(articleIdToUpdate,articleNameToUpDate, articleDescriptionToUpDate,articleDurationToUpDate,courseFormatToUpdate,newUpdatedCoursePrice,newUpdatedCourseCategory));
	}
	/**
	 * Méthode qui supprime une formation
	 */
	public static void deleteFormationFromShop() {
		System.out.println("Saisissez l'ID de la formation à supprimer: ");
		int articleIdToRemove = scan.nextInt();
		scan.nextLine();
		Article articleToRemove = DaoFactory.getArticleDao().read(articleIdToRemove);
		DaoFactory.getArticleDao().delete(articleToRemove);
	}
	
	
	/**
	 * Méthode qui ajoute une formation
	 */
	public static void addFormationToShop() {
		System.out.println("Saisissez le nom de la nouvelle formation: ");
		scan.nextLine();
		String newCourseName = scan.nextLine();
		System.out.println("Saisissez la description de la nouvelle formation: ");
		String newCourseDescription = scan.nextLine();
		System.out.println("Saisissez la durée de la nouvelle formation: ");
		int newCourseDuration = scan.nextInt();
		scan.nextLine();
		System.out.println("Quel est le format de la nouvelle formation: ");
		System.out.println("1 : Présentielle 2: Distancielle ");

		String newCourseFormat = "";
		int newCourseFormatChoice = scan.nextInt();
		scan.nextLine();

		switch (newCourseFormatChoice) {
		case 1: newCourseFormat = "Présentielle";
		break;
		case 2 : newCourseFormat = "Distancielle";
		}
		System.out.println("Quel est le prix de la nouvelle formation: ");
		double newCoursePrice = scan.nextDouble();
		scan.nextLine();
		
		System.out.println("Quel est la catégorie de la nouvelle formation: ");
		int newCourseCategory = scan.nextInt();
		scan.nextLine();
		DaoFactory.getArticleDao().create(new Article(newCourseName, newCourseDescription,newCourseDuration, newCourseFormat,newCoursePrice,newCourseCategory));
	}
	
	
	
	
	
	/**
	 * Méthode qui affiche tous les articles en base en centrant le texte
	 */
	public static void displayArticles() {
		// En-têtes des colonnes
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", COLUMN_ID, COLUMN_NAME,
				COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_FORMAT, COLUMN_PRICE);
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		// Lignes des articles
		business.readArticles().forEach(article -> {
			System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", article.getId(), article.getName(),
					article.getDescription(), article.getDuration(), article.getFormat(), article.getPrice());
		});
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
	}

	/**
	 * Méthode qui affiche tous les articles par catégorie en utilisant printf
	 */
	private static void displayArticlesByCategoryId() {
		System.out.println("saisissez l'id de la catégorie concerné");
		int id = scanInt();
		Category category = business.readOneCategory(id);
		if (category != null) {
			System.out.printf("              AFFICHAGE PAR CATEGORIE    %n");
			System.out.printf("                     %-10s               %n", category.getName());
			System.out.printf("---------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("%-15s | %-40s | %-40s | %-15s %n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_PRICE);
			System.out.printf("---------------------------------------------------------------------------------------------------------------%n");
			business.readArticlesByCatId(id).forEach(a -> System.out.printf("%-15s | %-40s | %-40s | %-15s %n",
					a.getId(), a.getName() ,a.getDescription(), a.getPrice()));
		} else
			System.out.println("cette catégorie n'existe pas !");
	}

	/**
	 * Méthode qui affiche tous les articles en remote
	 */
	private static void displayRemote() {
		ArrayList<Article> articles = business.readRemoteArticles();
		if (articles != null) {
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", COLUMN_ID, COLUMN_NAME,
					COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_FORMAT, COLUMN_PRICE);
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------------");
			articles.forEach(a -> System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", a.getId(),
					a.getName(), a.getDescription(), a.getDuration(), a.getFormat(), a.getPrice()));
		} else
			System.out.println("aucune formation trouvée !");
	}

	/**
	 * Méthode qui affiche tous les articles en remote
	 */
	private static void displayPresent() {
		ArrayList<Article> articles = business.readPresentArticles();
		if (articles != null) {
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", COLUMN_ID, COLUMN_NAME,
					COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_FORMAT, COLUMN_PRICE);
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------------");
			articles.forEach(a -> System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", a.getId(),
					a.getName(), a.getDescription(), a.getDuration(), a.getFormat(), a.getPrice()));
		} else
			System.out.println("aucune formation trouvée !");
	}

	/**
	 * Méthode qui affiche tous les articles par catégorie en utilisant printf
	 */
	private static void displayArticlesByKeyWord() {
		System.out.println("saisissez votre mot clé");
		String word = scan.next();
		ArrayList<Article> articles = business.readAllByKeyWord(word);
		if (articles != null) {
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", COLUMN_ID, COLUMN_NAME,
					COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_FORMAT, COLUMN_PRICE);
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------------");
			articles.forEach(a -> System.out.printf("%-5s | %-30s | %-40s | %-15s | %-15s | %-10s | %n", a.getId(),
					a.getName(), a.getDescription(), a.getDuration(), a.getFormat(), a.getPrice()));
		} else
			System.out.println("aucune formation trouvée !");
	}

	/**
	 * Méthode qui affiche toutes les catégories
	 */
	private static void displayCategories() {
		System.out.printf("              AFFICHAGE PAR CATEGORIE    %n");
		System.out.printf("----------------------------------------------------------------------------------------%n");
		System.out.printf("%-15s | %-50s | %-15s | %n", COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_NAME);
		System.out.printf("----------------------------------------------------------------------------------------%n");
		business.readCategories().forEach(a -> System.out.printf("%-15s | %-50s | %-15s %n",
				a.getId(), a.getDescription(), a.getName()));
	}

	/**
	 * Méthode qui supprime un article du panier
	 */
	public static void removeArticle() {
		System.out.println("Selectionner l'id de la formation à supprimer du panier");
		int id = scanInt();
		business.rmFromCart(id);
		displayCart(false);
	}

	/**
	 * Méthode qui ajoute un article au panier
	 */
	public static void addArticle() {
		System.out.println("Selectionner l'id de la formation à ajouter au panier");
		int id = scanInt();
		Article article = business.readOneArticle(id);
		if (article != null) {
			business.addToCart(article);
			displayCart(false);
		} else
			System.out.println("la formation que vous souhaitez ajouter n'existe pas, pb de saisi id");
	}

	/**
	 * Méthode qui affiche le contenu du panier + total de la commande + propose de
	 * passer commande
	 */
	private static void displayCart(boolean flag) {
		if (business.isCartEmpty())
			System.out.println("PANIER VIDE");
		else {
			System.out.println("CONTENU DU PANIER :");
			String titles = Article.centerString(COLUMN_ID) + Article.centerString(COLUMN_NAME)
					+ Article.centerString(COLUMN_DESCRIPTION) + Article.centerString(COLUMN_PRICE)
					+ Article.centerString("QUANTITE");
			System.out.println(titles);
			business.getCart().forEach(
					a -> System.out.println(a.toString() + Article.centerString(String.valueOf(a.getQuantity()))));
			if (flag) {
				System.out.println("MONTANT TOTAL : " + business.getTotal());
				System.out.println("Souhaitez vous passer commande ? Oui/Non :");
				if (scan.next().equalsIgnoreCase("Oui")) {
					nextStep();
				}
			}
		}
	}

	/**
	 * Méthode qui affiche toute les commandes en base
	 */
//	rsId,rsAmount,rsDate,rsIdCustomer
	public static void displayOrders() {
		// En-têtes des colonnes
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-5s | %-30s | %-40s | %-15s %n", COLUMN_ORDERID, COLUMN_PRICE, COLUMN_ORDERDATE, COLUMN_ORDERCUSTOMERID);
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		// Lignes des articles
		business.readAllOrders().forEach(order -> {
			System.out.printf("%-5s | %-30s | %-40s | %-15s %n", order.getIdOrder(), order.getAmount(), order.getDate(), order.getIdCustomer());
		});
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	
	
	/**
	 * Méthode qui affiche toute les commandes en base
	 */
//	rsId,rsAmount,rsDate,rsIdCustomer
	public static void displayOrderByCustomerId() {
		System.out.println("saisissez l'id du client");
		int id = scan.nextInt();
		scan.nextLine();
		ArrayList<Order> orders = business.readOrderByCustomerId(id);
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-5s | %-30s | %-40s | %-15s %n", COLUMN_ORDERID, COLUMN_PRICE, COLUMN_ORDERDATE, COLUMN_ORDERCUSTOMERID);
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
		// Lignes des articles
		orders.forEach(order -> {
			System.out.printf("%-5s | %-30s | %-40s | %-15s %n", order.getIdOrder(), order.getAmount(), order.getDate(), order.getIdCustomer());
		});
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------");
	}
	/**
	 * Méthode qui passe la commande, l'utilisateur doit être connecté si c'est le
	 * cas, l'utilisateur sera invité à associé un client à sa commande si le client
	 * n'existe pas, il devra le créer puis une commande associée au client sera
	 * ajoutée en base De même, des commandes minifiées seront associées à la
	 * commande une fois toutes les opérations terminées correctement, le panier
	 * sera vidé et un numéro de commande attribué
	 */
	private static void nextStep() {
		if (login == null) {
			System.out.println("Vous devez être connecté pour continuer");
			connection();
		}
		if (login != null) {
			int idCustomer = newCustomer(idUser);
			if (idCustomer != 0) {
				int idOrder = business.order(idCustomer);
				if (idOrder == 0)
					System.out.println("pb lors du passage de commande");
				else {
					System.out.println("Votre commande a bien été validé, voici son numéro : " + idOrder);
					business.clearCart();
				}
			}
		}
	}

	/**
	 * Méthode qui ajoute un client en base s'il n'existe pas déjà
	 * 
	 * @return id du client afin de l'associer à la commande en cours
	 */
	private static int newCustomer(int idUser) {
		System.out.println("Avez vous déjà un compte client ? Saisissez une adresse email valide :");
		String email = scan.next();
		if (isValidEmail(email)) {
			Customer customer = authenticate.existCustomerByEmail(email);
			if (customer == null) {
				System.out.println("Nous n'avons pas de compte client associé, nous allons le créer ");
				scan.nextLine();
				System.out.println("saisissez votre nom :");
				String name = scan.nextLine();
				System.out.println("saisissez votre prénom :");
				String fName = scan.next();
				System.out.println("saisissez votre tel :");
				String tel = scan.next();
				scan.nextLine();
				System.out.println("saisissez votre adresse :");
				String address = scan.nextLine();
				Customer cust = new Customer(name, fName, email, tel, address, idUser);
				if (authenticate.addCustomer(cust))
					return authenticate.existCustomerByEmail(email).getIdCustomer();
			} else {
				System.out.println("Nous allons associer la commande en cours avec le compte client : " + customer);
				return customer.getIdCustomer();
			}
		} else
			System.out.println("vous n'avez pas saisi un email valide");
		return 0;
	}

	/**
	 * Méthode qui réalise la connexion/deconnexion d'un utilisateur si
	 * l'utilisateur n'existe pas, il lui est proposé d'en créer un
	 */
	private static void connection() {
		if (login != null) {
			System.out.println("Souhaitez vous vous déconnecter ? Oui/Non");
			String response = scan.next();
			if (response.equalsIgnoreCase("Oui")) {
				System.out.println("A bientôt " + login + TEXT_RESET);
				login = null;
				idUser = 0;
			}
		} else {
			System.out.println("saisissez votre login : ");
			String log = scan.next();
			System.out.println("saisissez votre password : ");
			String pwd = scan.next();

			int id = authenticate.existUser(log, pwd);
			if (id > 0) {
				login = log;
				idUser = id;
				System.out.print(TEXT_BLUE);
			} else {
				System.out.println("login ou password incorrect");
				System.out.println("Nouvel utilisateur, pour créer un compte, tapez ok");
				String ok = scan.next();
				if (ok.equalsIgnoreCase("ok")) {
					newUser();
				}
			}
		}
	}

	/**
	 * Méthode qui réalise la connexion/deconnexion d'un utilisateur si
	 * l'utilisateur n'existe pas, il lui est proposé d'en créer un
	 */
	private static void adminConnection() {
		UserDao userDao = new UserDao();
		if (login != null) {
			System.out.println("Souhaitez vous vous déconnecter ? Oui/Non");
			String response = scan.next();
			if (response.equalsIgnoreCase("Oui")) {
				System.out.println("A bientôt " + login + TEXT_RESET);
				login = null;
				idUser = 0;
				userRole = null;
			}
		} else {
			System.out.println("saisissez votre login : ");
//			String log = scan.next();
			String log = "Lara";
			System.out.println("saisissez votre password : ");
//			String pwd = scan.next();
			String pwd = "Croft";
			
			int id = authenticate.existUser(log, pwd);
			User admin = userDao.read(id);
			userRole = admin.getRole();
			if ((id > 0) && (userRole.equals("Admin"))) {
				login = log;
				idUser = id;
				System.out.print(TEXT_GREEN);
			} else {
				System.out.println("accès non autorisé");
			}
			}
		}
	

	/**
	 * Méthode qui ajoute un nouvel utilisateur en base
	 */
	public static void newUser() {
		System.out.println("saisissez un login :");
		String login = scan.next();
		int id = authenticate.existUser(login);
		if (id == 0) {
			System.out.println("saisissez votre password :");
			String password = scan.next();
			authenticate.addUser(login, password);
			System.out.println("Ne perdez pas ces infos de connexion...");
			stop(2);
			System.out.println("création de l'utilisateur terminé, merci de vous connecter");
		} else
			System.out.println("Login déjà existant en base, veuillez vous connecter");
	}

	public static void stop(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int scanInt() {
		while (!scan.hasNextInt()) {
			System.out.println("saisissez une valeur entière svp");
			scan.next();
		}
		return scan.nextInt();
	}

	public static boolean isValidEmail(String email) {
		String regExp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z][a-z]+$";
		return email.matches(regExp);
	}
}
