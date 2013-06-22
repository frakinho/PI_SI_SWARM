package DataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import config.config;

import Entidades.caso;
import Entidades.movTabuleiro;
import Entidades.movimento;
import Entidades.particle;

public class Connect {
	private Connection connect = null; // Cria a liga‹o
	private Statement statement = null; // Restantes para a query
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	// Construtor para ligar
	public Connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setup the connection with the DB

		try {
			connect = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SISwarm", "root",
					"que34almeida25");
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addParticle(particle p) {
		try {
			preparedStatement = connect
					.prepareStatement("INSERT INTO SISwarm.PARTICLE (x,y,image,distCentro,tipo) values (?,?,?,?,?)");
			preparedStatement.setInt(1, p.getX());
			preparedStatement.setInt(2, p.getY());
			preparedStatement.setString(3, p.getImage());
			preparedStatement.setDouble(4, p.getDistCentro());
			preparedStatement.setInt(5, p.getType());
			preparedStatement.executeUpdate();

			resultSet = statement
					.executeQuery("SELECT id FROM PARTICLE WHERE x=" + p.getX()
							+ " and y=" + p.getY() + " and tipo ="
							+ p.getType() + ";");
			int id = -1;
			while (resultSet.next()) {
				id = Integer.parseInt(resultSet.getString("id"));
			}
			p.setId(id);

		} catch (SQLException e) {
			System.out.println("Erro");
			e.printStackTrace();
		} finally {
			// close();
		}
	}

	public void addCaso(caso caso) {
		// TODO Auto-generated method stub

		this.addMovimento(caso.getMovimento());
		this.addParticle(caso.getParticula());
		try {
			preparedStatement = connect
					.prepareStatement("INSERT INTO SISwarm.CASO (id_PARTICLE,id_MOVIMENTO,id_PROBLEMA,SUCCESS) values (?,?,?,?)");
			preparedStatement.setInt(1, caso.getParticula().getId());
			System.out.println("Id da Particula: "
					+ caso.getParticula().getId());
			preparedStatement.setInt(2, caso.getMovimento().getId());
			preparedStatement.setInt(3, caso.getProblema().getId());
			preparedStatement.setInt(4, caso.getSucesso());
			preparedStatement.executeUpdate();
			System.out.println("CASO ADICIONADO COM SUCESSO");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // close();

		}

	}

	public void addMovimento(movimento movimento) {
		try {
			String query = "INSERT INTO SISwarm.MOVIMENTO (mov_x,mov_y) values ("
					+ movimento.getMov_x() + "," + movimento.getMove_y() + ")";

			int id = preparedStatement.executeUpdate(query,
					Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			movimento.setId(id);
			System.out.println("ID: " + id);
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			// close();
		}
	}

	// Actualiza movimento
	public particle updateMovimento(particle p, config config) {
		particle nova = null;
		try {
			String query = "SELECT AVG(MOV.mov_x) mov_x,AVG(MOV.mov_y) mov_y "
					+ "FROM MOVIMENTO MOV,CASO C,PARTICLE PAT "
					+ "WHERE C.id_MOVIMENTO = MOV.id and C.id_PARTICLE = PAT.id and PAT.x > "
					+ (p.getX() - config.getTolerancia()) + " and PAT.x < "
					+ (p.getX() + config.getTolerancia()) + ";";

			resultSet = statement.executeQuery(query);
			double andaX = 0;
			try {
				while (resultSet.next()) {
					andaX = Double.parseDouble(resultSet.getString("mov_x"));
					andaX *= config.getVelocidade();
					// p.setX(((int) andaX) + p.getX());
				}

				String queryY = "SELECT AVG(MOV.mov_y) mov_y "
						+ "FROM MOVIMENTO MOV,CASO C,PARTICLE PAT "
						+ "WHERE C.id_MOVIMENTO = MOV.id and C.id_PARTICLE = PAT.id and PAT.y > "
						+ (p.getY() - config.getTolerancia()) + " and PAT.y < "
						+ (p.getY() + config.getTolerancia()) + ";";

				resultSet = statement.executeQuery(queryY);

				double andaY = 0;

				while (resultSet.next()) {
					andaY = Double.parseDouble(resultSet.getString("mov_y"));
					andaY *= config.getVelocidade();

					// p.setY(((int) andaY) + p.getY());

				}

				if (p.getX() > 180 && p.getX() < 225 && p.getY() < 200
						&& p.getY() > 160) {
					andaY += 4;
					p.setDebug("Aqui");
				}

				// anda para as flores
				if (p.getLocate() == 0) {
					andaX *= -1;
					andaY *= -1;
				}

				Random r = new Random();
				andaX *= r.nextInt(2);
				andaY *= r.nextInt(2);
				nova = new particle((int) andaX + p.getX(), (int) andaY
						+ p.getY(), p.getType(), p.getImage(),
						p.getDistCentro());

				// caso caso = new caso(p, new problema(1, false, false,false,
				// false), new movimento((int) andaX, (int) andaY), 1);
				// addCaso(caso);
			} catch (NullPointerException e) {
				p.setDebug("NaN");
				System.out.println("N‹o existe valor para esta posi‹o");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nova;

	}

	public movTabuleiro getValores(int xInit, int xFinal, int yInit, int yFinal) {
		double mov_X = 0;
		double mov_Y = 0;
		movTabuleiro nova = null;
		try {
			String query = "SELECT AVG(MOV.mov_x) mov_x,AVG(MOV.mov_y) mov_y "
					+ "FROM MOVIMENTO MOV,CASO C,PARTICLE PAT "
					+ "WHERE C.id_MOVIMENTO = MOV.id and C.id_PARTICLE = PAT.id and PAT.x > "
					+ xInit + " and PAT.x < " + xFinal + " and PAT.y > "
					+ yInit + " and PAT.y < " + +yFinal + ";";

			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				mov_X = Double.parseDouble(resultSet.getString("mov_x"));
				mov_Y = Double.parseDouble(resultSet.getString("mov_y"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e) {
			
		}
		nova = new movTabuleiro(mov_X, mov_Y);
		return nova;

	}

	/*************************
	 * CATEGORIAS ***********************
	 * */
	/**
	 * // Adiciona categoria public void addCategoria(Categoria categoria) { try
	 * { preparedStatement = connect .prepareStatement(
	 * "INSERT INTO Talho.CATEGORIA (nome,descricao,preco) values (?,?,?);");
	 * preparedStatement.setString(1, categoria.getNome());
	 * preparedStatement.setString(2, categoria.getDescricao());
	 * preparedStatement.setString(3, categoria.getPreco() + "");
	 * preparedStatement.executeUpdate();
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * System.out.println("Erro ao inserir na Base De Dados");
	 * e.printStackTrace(); } finally { // No fim fecha a conexao close(); } }
	 * 
	 * public ArrayList<Categoria> loadCategoria() { ArrayList<Categoria> lista
	 * = new ArrayList<Categoria>();
	 * 
	 * try {
	 * 
	 * resultSet = statement.executeQuery("SELECT * FROM TALHO.CATEGORIA");
	 * 
	 * while (resultSet.next()) { int id = resultSet.getInt(1); // ID
	 * 
	 * String nome = resultSet.getString("nome"); // Nome String descricao =
	 * resultSet.getString("descricao");// Descricao float preco =
	 * resultSet.getFloat(4); Categoria c = new Categoria(id, nome, descricao,
	 * preco); lista.add(c); } } catch (SQLException e) { // TODO Auto-generated
	 * catch block System.out.println("Erro ao inserir na Base De Dados");
	 * e.printStackTrace(); } finally { // No fim fecha a conexao close(); }
	 * 
	 * return lista; }
	 */
	/*********************************
	 * ENTRADAS ******************************
	 */
	/**
	 * public void addEntrada(Entrada entrada) { try { preparedStatement =
	 * connect .prepareStatement(
	 * "INSERT INTO Talho.ENTRADA (id_Categoria,dataEntrada,descricao,preco) values (?,?,?,?);"
	 * ); preparedStatement.setInt(1, entrada.getCategoria().getId());
	 * preparedStatement.setString(2, entrada.getDateInserirBaseDados());
	 * preparedStatement.setString(3, entrada.getDescricao());
	 * preparedStatement.setFloat(4, entrada.getPreco());
	 * preparedStatement.executeUpdate();
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block System.out
	 * .println("Erro ao inserir Entrada na BD na Base De Dados");
	 * e.printStackTrace(); } finally { // No fim fecha a conexao close(); } }
	 * 
	 * // faz load das categorias sem nenhum criterio isto tem que se mudar
	 * depois
	 * 
	 * @SuppressWarnings("deprecation") public ArrayList<Entrada>
	 *                                  loadEntrada(Filtro filtro) {
	 *                                  ArrayList<Entrada> entradas = new
	 *                                  ArrayList<Entrada>(); try { switch
	 *                                  (filtro.getId()) { case 0: resultSet =
	 *                                  statement .executeQuery(
	 *                                  "SELECT CATEGORIA.NOME, ENTRADA.dataEntrada,ENTRADA.descricao, ENTRADA.preco "
	 *                                  +
	 *                                  "FROM CATEGORIA,ENTRADA WHERE CATEGORIA.id = ENTRADA.id_categoria ORDER BY ENTRADA.dataEntrada DESC;"
	 *                                  ); break; case 1: resultSet = statement
	 *                                  .executeQuery(
	 *                                  "SELECT CATEGORIA.NOME, ENTRADA.dataEntrada,ENTRADA.descricao, ENTRADA.preco "
	 *                                  +
	 *                                  "FROM CATEGORIA,ENTRADA WHERE CATEGORIA.id = ENTRADA.id_categoria and ENTRADA.dataEntrada >= '"
	 *                                  + filtro.getDataComparacaoInicio() +
	 *                                  "' and ENTRADA.dataEntrada <= '" +
	 *                                  filtro.getDataComparacaoFim() + "';");
	 *                                  break; case 2: break; case 3: resultSet
	 *                                  = statement .executeQuery(
	 *                                  "SELECT CATEGORIA.NOME, ENTRADA.dataEntrada,ENTRADA.descricao, ENTRADA.preco "
	 *                                  +
	 *                                  "FROM CATEGORIA,ENTRADA WHERE CATEGORIA.id = ENTRADA.id_categoria and id_categoria = "
	 *                                  + filtro.getCategoria().getId() + ";");
	 *                                  break; }
	 * 
	 *                                  while (resultSet.next()) { String nome =
	 *                                  resultSet.getString("nome"); // Nome
	 *                                  Date data = resultSet.getDate(2); String
	 *                                  descricao =
	 *                                  resultSet.getString("descricao");//
	 *                                  Descricao float preco =
	 *                                  resultSet.getFloat(4); // Categoria so
	 *                                  com o nome esero nao ter problemas aqui
	 *                                  Categoria categoria = new Categoria();
	 *                                  categoria.setNome(nome);
	 *                                  data.setYear(data.getYear() + 1900);
	 *                                  Entrada c = new Entrada(data, descricao,
	 *                                  categoria, preco); entradas.add(c); } }
	 *                                  catch (SQLException e) { // TODO
	 *                                  Auto-generated catch block
	 *                                  e.printStackTrace(); } return entradas;
	 *                                  }
	 * 
	 *                                  /** SAIDAS
	 * 
	 */
	/**
	 * public void addSaida(Saida saida) { // TODO Auto-generated method stub
	 * try { preparedStatement = connect .prepareStatement(
	 * "INSERT INTO Talho.SAIDA (id_Categoria,dataSaida,descricao,preco) values (?,?,?,?);"
	 * ); preparedStatement.setInt(1, saida.getCategoria().getId());
	 * preparedStatement.setString(2, saida.getDateInserirBaseDados());
	 * preparedStatement.setString(3, saida.getDescricao());
	 * preparedStatement.setFloat(4, saida.getPreco());
	 * preparedStatement.executeUpdate();
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block System.out
	 * .println("Erro ao inserir Entrada na BD na Base De Dados");
	 * e.printStackTrace(); } finally { // No fim fecha a conexao close(); } }
	 * 
	 * @SuppressWarnings("deprecation") public ArrayList<Saida> loadSaida(Filtro
	 *                                  filtro) { // TODO Auto-generated method
	 *                                  stub ArrayList<Saida> saidas = new
	 *                                  ArrayList<Saida>();
	 * 
	 *                                  try { switch (filtro.getId()) { case 0:
	 *                                  resultSet = statement .executeQuery(
	 *                                  "SELECT CATEGORIA.NOME, SAIDA.dataSaida,SAIDA.descricao, SAIDA.preco "
	 *                                  +
	 *                                  "FROM CATEGORIA,SAIDA WHERE CATEGORIA.id = SAIDA.id_categoria ORDER BY SAIDA.dataSaida DESC;"
	 *                                  ); break; case 1: resultSet = statement
	 *                                  .executeQuery(
	 *                                  "SELECT CATEGORIA.NOME, SAIDA.dataSaida,SAIDA.descricao, SAIDA.preco "
	 *                                  +
	 *                                  "FROM CATEGORIA,SAIDA WHERE CATEGORIA.id = SAIDA.id_categoria and SAIDA.dataEntrada >= '"
	 *                                  + filtro.getDataComparacaoInicio() +
	 *                                  "' and SAIDA.dataEntrada <= '" +
	 *                                  filtro.getDataComparacaoFim() + "';");
	 *                                  break; case 2: break; case 3: resultSet
	 *                                  = statement .executeQuery(
	 *                                  "SELECT CATEGORIA.NOME, SAIDA.dataSaida,SAIDA.descricao, SAIDA.preco "
	 *                                  +
	 *                                  "FROM CATEGORIA,SAIDA WHERE CATEGORIA.id = SAIDA.id_categoria and id_categoria = "
	 *                                  + filtro.getCategoria().getId() + ";");
	 *                                  break; }
	 * 
	 *                                  while (resultSet.next()) { String nome =
	 *                                  resultSet.getString("nome"); // Nome
	 *                                  Date data = resultSet.getDate(2); String
	 *                                  descricao =
	 *                                  resultSet.getString("descricao");//
	 *                                  Descricao float preco =
	 *                                  resultSet.getFloat(4); // Categoria so
	 *                                  com o nome esero nao ter problemas aqui
	 *                                  Categoria categoria = new Categoria();
	 *                                  categoria.setNome(nome);
	 *                                  data.setYear(data.getYear() + 1900);
	 *                                  Saida c = new Saida(data, descricao,
	 *                                  categoria, preco); saidas.add(c); } }
	 *                                  catch (SQLException e) { // TODO
	 *                                  Auto-generated catch block
	 *                                  e.printStackTrace(); } return saidas; }
	 * 
	 *                                  /***** RESULTADOS
	 */

	/**
	 * //vai receber uma data public ArrayList<Entrada>
	 * getResultadoPorMesEntrada(int mes) { //Uma hasmap com cada dia da semana
	 * e para cada dia tem cada categoria, tudo isto por mes ArrayList<Entrada>
	 * listaPorMes = new ArrayList<Entrada>(); try { resultSet = statement
	 * .executeQuery(
	 * "SELECT dataEntrada,id_categoria,preco FROM ENTRADA where dataEntrada >= '2013-"
	 * +mes+"-01' and dataEntrada <= '2013-"+mes+"-28' ;");
	 * 
	 * while(resultSet.next()) { Date data = resultSet.getDate(1); int
	 * id_categoria = resultSet.getInt("id_categoria"); float preco =
	 * resultSet.getFloat(3); Entrada entrada = new Entrada(); Categoria
	 * categoria = new Categoria(); //Cateogoria so com id para comparar
	 * categoria.setId(id_categoria); entrada.setDataEntrada(data);
	 * entrada.setCategoria(categoria); entrada.setPreco(preco);
	 * listaPorMes.add(entrada);
	 * 
	 * } } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return listaPorMes; }
	 * 
	 * public ArrayList<Entrada> getResultadoPorMesSaida(int mes) { //Uma hasmap
	 * com cada dia da semana e para cada dia tem cada categoria, tudo isto por
	 * mes ArrayList<Entrada> listaPorMes = new ArrayList<Entrada>(); try {
	 * resultSet = statement .executeQuery(
	 * "SELECT dataSaida,id_categoria,preco FROM Saida where dataSaida >= '2013-"
	 * +mes+"-01' and dataSaida <= '2013-"+mes+"-28' ;");
	 * 
	 * while(resultSet.next()) { Date data = resultSet.getDate(1); int
	 * id_categoria = resultSet.getInt("id_categoria"); float preco =
	 * resultSet.getFloat(3); Entrada entrada = new Entrada(); Categoria
	 * categoria = new Categoria(); //Cateogoria so com id para comparar
	 * categoria.setId(id_categoria); entrada.setDataEntrada(data);
	 * entrada.setCategoria(categoria); entrada.setPreco(preco);
	 * listaPorMes.add(entrada);
	 * 
	 * } } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return listaPorMes; }
	 * 
	 * /**************** OUTROS **************
	 * 
	 * @throws Exception
	 */
	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/feedback?"
							+ "user=sqluser&password=sqluserpw");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement
					.executeQuery("select * from FEEDBACK.COMMENTS");
			writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connect
					.prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// Remove again the insert comment
			preparedStatement = connect
					.prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();

			resultSet = statement
					.executeQuery("select * from FEEDBACK.COMMENTS");
			writeMetaData(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summery = resultSet.getString("summery");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summery: " + summery);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}