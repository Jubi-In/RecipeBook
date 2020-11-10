package persistence.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import service.dto.*;

public class RecipeDAO {

	private static JDBCUtil jdbcUtil = null;

	public RecipeDAO() {
		jdbcUtil = new JDBCUtil();
	}

	// ������ �߰� (recipeStep)
	public static int insertRecipe (Recipe rcp) {
		int result = 0;
		String sql = "INSERT INTO RECIPE (recipeId, recipeName, summary, nation, difficulty, image, report) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		Object[] param = new Object[] { rcp.getRecipeId(), rcp.getRecipeName(), rcp.getSummary(), rcp.getNation(),
				rcp.getDifficulty(), rcp.getImage(), rcp.getReport() };
		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			result = jdbcUtil.executeUpdate();
//			�Ű������� (List<RecipeStep> rcpStepList, List<RecipeIngredient> rcpIngList)�� �ްų�
//			rcp.getRecipeId() �� ���� ���� ������ List�� ���� �� �Ʒ��� �� �� ����
//			insertRecipeStep(rcpStepList);
//			insertRecipeIngredient(rcpStepList);
			if (result == 1)
				System.out.println("insert recipe success");
			else
				System.out.println("insert recipe failed");
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return result;
	}

	public static int insertRecipeStep (List<RecipeStep> rcpStepList) {
		int result = 0;
		String sql = "INSERT INTO RECIPESTEP (recipeId, stepNum, content) "
				+ "VALUES (?, ?, ?)";
//		�Ű����� (RecipeStep rcpStep)
//		Object[] param = new Object[] { rcpStep.getRecipeId(), rcpStep.getStepNum(), rcpStep.getContent() };
//		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			for (int i = 0; i < rcpStepList.size(); i++) {
				Object[] param = new Object[] { rcpStepList.get(i).getRecipeId(), rcpStepList.get(i).getStepNum(), rcpStepList.get(i).getContent() };
				jdbcUtil.setSqlAndParameters(sql, param);
				result += jdbcUtil.executeUpdate();
				jdbcUtil.close();
			}
//			result = jdbcUtil.executeUpdate();
//			if (result == 1)
			if (result == rcpStepList.size())
				System.out.println("insert recipeStep success");
			else {
				System.out.println("insert recipeStep failed");
				jdbcUtil.rollback();
			}
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return result;
	}

	public static int insertRecipeIngredient (List<RecipeIngredient> rcpIngList) {
		int result = 0;
		String sql = "INSERT INTO RECIPESTEP (recipeId, ingredientId, ingredientName, amount, unit) "
				+ "VALUES (?, ?, ?, ?, ?)";
//		�Ű����� (RecipeIngredient rcpIng)
//		Object[] param = new Object[] { rcpIng.getRecipeId(), rcpIng.getIngredientId(), rcpIng.getIngredientName()
//				, rcpIng.getAmount(), rcpIng.getUnit() };
//		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			for (int i = 0; i < rcpIngList.size(); i++) {
				Object[] param = new Object[] { rcpIngList.get(i).getRecipeId(), rcpIngList.get(i).getIngredientId()
						, rcpIngList.get(i).getIngredientName(), rcpIngList.get(i).getAmount(), rcpIngList.get(i).getUnit() };
				jdbcUtil.setSqlAndParameters(sql, param);
				result += jdbcUtil.executeUpdate();
				jdbcUtil.close();
			}
//			result = jdbcUtil.executeUpdate();
//			if (result == 1)
			if (result == rcpIngList.size())
				System.out.println("insert recipeIngredient success");
			else {
				System.out.println("insert recipeIngredient failed");
				jdbcUtil.rollback();
			}
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return result;
	}

	// ������ ����
	public static int updateRecipe (Recipe rcp, String userId) {

		if (rcp.getUserId() != userId) 
			System.out.println("recipe update failed because of incorrect Id");

		else {

			String sql = "UPDATE RECIPE SET ";
			int index = 0;

			Object[] tempParam = new Object[10];		// update ���� ����� �Ű������� ������ �� �ִ� �ӽ� �迭

			if (rcp.getRecipeName() != null) {		// �̸��� �����Ǿ� ���� ���
				sql += "recipeName = ?, ";		// update ���� �̸� ���� �κ� �߰�
				tempParam[index++] = rcp.getRecipeName();		// �Ű������� ������ �̸� �߰�
			}
			if (rcp.getSummary() != null) {		// summary�� �����Ǿ� ���� ���
				sql += "summary = ?, ";		// update ���� summary ���� �κ� �߰�
				tempParam[index++] = rcp.getSummary();		// �Ű������� ������ summary �߰�
			}
			if (rcp.getNation() != null) {		// ������ �����Ǿ� ���� ���
				sql += "nation = ?, ";		// update ���� ���� ���� �κ� �߰�
				tempParam[index++] = rcp.getNation();		// �Ű������� ������ ���� �߰�
			}
			if (rcp.getDifficulty() != null) {		// ���̵��� �����Ǿ� ���� ���
				sql += "difficulty = ?, ";		// update ���� ���̵� ���� �κ� �߰�
				tempParam[index++] = rcp.getDifficulty();		// �Ű������� ������ ���̵� �߰�
			}
			if (rcp.getImage() != null) {		// �̹����� �����Ǿ� ���� ���
				sql += "image = ?, ";		// update ���� �̹��� ���� �κ� �߰�
				tempParam[index++] = rcp.getImage();		// �Ű������� ������ �̹��� �߰�
			}

			sql += "WHERE recipeId = ? ";		// update ���� ���� ����
			sql = sql.replace(", WHERE", " WHERE");		// update ���� where �� �տ� ���� �� �ִ� , ����

			tempParam[index++] = rcp.getRecipeId();		// ã�� ���ǿ� �ش��ϴ� �й��� ���� �Ű����� �߰�

			Object[] newParam = new Object[index];
			for (int i=0; i < newParam.length; i++)		// �Ű������� ������ŭ�� ũ�⸦ ���� �迭�� �����ϰ� �Ű����� �� ����
				newParam[i] = tempParam[i];

			jdbcUtil.setSqlAndParameters(sql, newParam);

			try {
				int result = jdbcUtil.executeUpdate();		// update �� ����
				return result;			// update �� ���� �ݿ��� ���ڵ� �� ��ȯ
			} catch (Exception ex) {
				jdbcUtil.rollback();
				ex.printStackTrace();
			}
			finally {
				jdbcUtil.commit();
				jdbcUtil.close();		// ResultSet, PreparedStatement, Connection ��ȯ
			}		
		}
		return 0;
	}

	// ������ ����
	public static void deleteRecipe (Recipe rcp, String userId) {

		if (rcp.getUserId() != userId) 
			System.out.println("recipe update failed because of incorrect Id");

		else {
			deleteRecipeStep(rcp.getRecipeId());
			deleteRecipeIngredient(rcp.getRecipeId());

			String sql = "DELETE FROM RECIPE WHERE recipeId = ?";
			jdbcUtil.setSqlAndParameters(sql, new Object[] {rcp.getRecipeId()});

			try {
				int result = jdbcUtil.executeUpdate();
				if (result == 1)	System.out.println("delete recipe success");
				else	System.out.println("delete recipe failed");
			} catch (Exception ex) {
				jdbcUtil.rollback();
				jdbcUtil.close();
			}
		}
	}
	
	public static void deleteRecipeStep (String recipeId) {
		String sql = "DELETE FROM RECIPESTEP WHERE recipeID = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {recipeId});

		try {
			int result = jdbcUtil.executeUpdate();
			if (result > 0)		System.out.println("delete recipeSteps success");
			else	System.out.println("delete recipeSteps failed");
		} catch (Exception ex) {
			jdbcUtil.rollback();
			jdbcUtil.close();
		}
	}

	public static void deleteRecipeIngredient (String recipeId) {
		String sql = "DELETE FROM RECIPE_INGREDIENT WHERE recipeId = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {recipeId});

		try {
			int result = jdbcUtil.executeUpdate();
			if (result > 0)		System.out.println("delete recipe_ingredient success");
			else	System.out.println("delete recipe_ingredient failed");
		} catch (Exception ex) {
			jdbcUtil.rollback();
			jdbcUtil.close();
		}
	}

	// �������� ������ DTO ��ȯ (�˻����)
	public static List<Recipe> getRecipeListByIngredient(List<Ingredient> ingredientList) {
		int size = ingredientList.size();

		String sql = "SELECT recipeId, recipeName, userId, summary, nation, difficulty, image, report "
				+ "FROM RECIPE r, RECIPE_INGREDIENT ri "
				+ "WHERE ingredientid = ? "; 

		for (int i = 0; i < ingredientList.size()-1; i++)
			sql += " or ingredientId = ? ";

		Object[] param = new Object[size];
		for (int i = 0; i < ingredientList.size(); i++)
			param[i++] = ingredientList.get(i++).getIngredientId();

		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			List<Recipe> rcpList = new ArrayList<Recipe>();
			while (rs.next()) {	
				Recipe rcp = new Recipe();
				rcp.setRecipeId(rs.getString("recipeId"));
				rcp.setRecipeName(rs.getString("recipeName"));
				rcp.setUserId(rs.getString("userId"));
				rcp.setSummary(rs.getString("summary"));
				rcp.setNation(rs.getString("nation"));
				rcp.setDifficulty(rs.getString("difficulty"));
				rcp.setImage(rs.getString("image"));
				rcp.setReport(rs.getInt("report"));
				rcpList.add(rcp);
			}
			if (rcpList.isEmpty())
				System.out.println("empty recipe");
			return rcpList;				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();	
		}
		return null;
	}

	// recipeName���� ������ DTO ��ȯ (�˻����)
	public static List<Recipe> getRecipeListByName(String recipeName) {
		String sql = "SELECT recipeId, recipeName, userId, summary, nation, difficulty, image, report "
				+ "FROM RECIPE "
				+ "WHERE recipeName = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {recipeName});

		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			List<Recipe> rcpList = new ArrayList<Recipe>();
			while (rs.next()) {	
				Recipe rcp = new Recipe();
				rcp.setRecipeId(rs.getString("recipeId"));
				rcp.setRecipeName(rs.getString("recipeName"));
				rcp.setUserId(rs.getString("userId"));
				rcp.setSummary(rs.getString("summary"));
				rcp.setNation(rs.getString("nation"));
				rcp.setDifficulty(rs.getString("difficulty"));
				rcp.setImage(rs.getString("image"));
				rcp.setReport(rs.getInt("report"));
				rcpList.add(rcp);
			}
			if (rcpList.isEmpty())
				System.out.println("empty refrigerator");
			return rcpList;				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		return null;
	}
}
