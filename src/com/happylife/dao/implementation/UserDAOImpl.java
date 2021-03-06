package com.happylife.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.happylife.DoMath;
import com.happylife.dao.layer.LookingForDAO;
import com.happylife.dao.layer.LookingForDAOException;
import com.happylife.dao.layer.UserDAO;
import com.happylife.dao.layer.UserDAOException;
import com.happylife.dao.registry.RegistryDAO;
import com.happylife.pojo.LookingFor;
import com.happylife.pojo.Messages;
import com.happylife.pojo.User;

public class UserDAOImpl implements UserDAO {
	static Connection conn;
	static PreparedStatement pstmt;
	static ResultSet rs;
	
	@Override
	public User doLogin(String email, String password) throws UserDAOException {
		User userToFetch = null;
		
		try{
			conn = DatabaseConnectivity.doDBConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from HL_USERS where EMAIL=? and PASSWD=?"); 
			pstmt.setString(1,email);
			pstmt.setString(2,password);
			
			rs = pstmt.executeQuery();
			
			//ResultSet rs = statement.executeQuery("select * from HL_USERS where EMAIL='"+email+"' and PASSWD='"+password+"'");
			System.out.println("select count(*) from HL_USERS where EMAIL='"+email+"' and PASSWD='"+password+"'");
			int columnCount = 0;
			while(rs.next()){
				for (int i = 1; i <= columnCount; i++ ) {
					  String name = rs.getMetaData().getColumnName(i);
					  System.out.println("Column name: "+ name);
					}
				long userId  = rs.getLong(1);					//rs.getLong("userId");
				String fname  = rs.getString(2);				//rs.getString("FNAME");
				String lname = rs.getString(3);					//rs.getString("LNAME");
				String emailId  = rs.getString(4);				//rs.getString("EMAIL");
				String uname  = rs.getString(5);				//rs.getString("username");
				String pass  = rs.getString(6);					//rs.getString("PASSWD");
				String gender  = rs.getString(7);				//rs.getString("GENDER");
				String country  = rs.getString(8);				//rs.getString("COUNTRY");
				String phone  = rs.getString(9);				//rs.getString("PHONE");
				String image  = rs.getString(10);				//rs.getString("IMAGE");
				Date dob = rs.getDate(11);
				String age = rs.getString(12);
				String residencyStatus = rs.getString(13);
				String aboutMyself = rs.getString(14);
				String lookingFor = rs.getString(15);
				String publicPhoto  = rs.getString(16);			//rs.getString("PUBLIC_PHOTO");
				Timestamp lastLogin = rs.getTimestamp(17);
				String profilePostedBy  = rs.getString(18);
				String origin  = rs.getString(19);
				String religiousHistory  = rs.getString(20);
				String hairColor  = rs.getString(21);
				String bodyType  = rs.getString(22);
				String hijabBeard  = rs.getString(24);
				String height = rs.getString(25);
				String pray = rs.getString(26);
				String sect = rs.getString(27);
				String maritalStatus = rs.getString(28);
				String children = rs.getString(29);
				String likeToHaveChildren = rs.getString(30);
				String langs = rs.getString(31);
				String profession = rs.getString(32);
				String highestQual = rs.getString(33);
				
				System.out.println("Height fetched from database inside UserDAOImpl.dologin: " + height);
				User user = new User(userId, fname, lname, emailId, uname, gender, country, phone, image, dob, residencyStatus, 
						aboutMyself, lookingFor, publicPhoto, lastLogin, profilePostedBy, origin, religiousHistory, hairColor,
						bodyType, hijabBeard, height, pray, sect, maritalStatus, children, likeToHaveChildren, langs, 
						profession, highestQual );
				userToFetch = user;
			}
			
			java.util.Date date = new java.util.Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			System.out.println("Timestamp after login is " + ts);
			pstmt = conn.prepareStatement("update HL_USERS set lastlogin=? where userId=?");
			pstmt.setLong(2,userToFetch.getUserId());
			pstmt.setTimestamp(1,ts);
			pstmt.executeUpdate();
			
			
			if(userToFetch == null){
				throw new UserDAOException("User not found");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
		return userToFetch;
	}
	
	
	
	@Override
	public String doSignUp(User user) throws UserDAOException{
		boolean status = false;
		String signupStatus = "";
		try {
			conn = DatabaseConnectivity.doDBConnection();
			pstmt = conn.prepareStatement("insert into HL_USERS values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			
			pstmt.setLong(1,user.getUserId());
			pstmt.setString(2,user.getFname());
			pstmt.setString(3,user.getLname());
			pstmt.setString(4,user.getEmail());
			pstmt.setString(5,user.getUsername());
			pstmt.setString(6,user.getPassword());
			pstmt.setString(7,user.getGender());
			pstmt.setString(8,user.getLookingIn());
			pstmt.setString(9,user.getPhone());
			pstmt.setString(10,"0");
			pstmt.setDate(11,user.getDob());
			pstmt.setInt(12,user.getAge(user.getDob()));
			pstmt.setString(13,null);
			pstmt.setString(14,null);
			pstmt.setString(15,null);
			pstmt.setString(16,user.getPublicPhoto());
			java.util.Date date = new java.util.Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			pstmt.setTimestamp(17,ts);
			pstmt.setString(18,null);
			pstmt.setString(19,null);
			pstmt.setString(20,null);
			pstmt.setString(21,null);
			pstmt.setString(22,null);
			pstmt.setString(23,null);
			pstmt.setString(24,null);
			pstmt.setString(25,null);
			pstmt.setString(26,null);
			pstmt.setString(27,null);
			pstmt.setString(28,null);
			pstmt.setString(29,null);
			pstmt.setString(30,null);
			pstmt.setString(31,null);
			pstmt.setString(32,null);
			pstmt.setString(33,null);
			status = pstmt.execute();
						
			//statment.executeQuery("INSERT INTO 'spring'.'HL_USERS' ('userId', 'FNAME', 'LNAME', 'EMAIL', 'USERNAME', 'PASSWD', 'GENDER', 'COUNTRY', 'PHONE', 'PUBLIC_PHOTO') VALUES ('"+user.getId()+"', '"+user.getFname()+"', '"+user.getLname()+"', '"+user.getEmail()+"', '"+user.getUsername()+"', '"+user.getPassword()+"', '"+user.getGender()+"', '"+user.getCountry()+"', '"+user.getPhone()+"', '"+user.getPublicPhoto()+"')");
			
			signupStatus =  "Sign Up Successfully...";
		}catch(SQLException e) {
			System.out.println(e);
			signupStatus =  "Something went wrong with user signup please try again ! ! !";
		}
		
		return signupStatus;
	}

	@Override
	public List<User> searchBy(User sessionUser, String... v) throws UserDAOException {
		List<User> candidateList = new ArrayList<User>();
		String matchGender = getMatchGender(sessionUser.getGender());
		System.out.println("matchGender is:" + matchGender);
		
		if (v.length %2 != 0) { 
			System.out.println( "wrong number of arguments, number of Arguments must be even" );
			return null;
		}
		try {
			conn = DatabaseConnectivity.doDBConnection();
			for(int i=0 ; i< v.length; i+=2) {
				switch(v[i]) {
				case "location":
					System.out.println("Inside searchBy case location");
					candidateList = getByLocation(sessionUser, v[i+1]);
					if(candidateList == null) throw new UserDAOException("Your Location Search Criteria is not met") ;
					
					break;
					
				case "lookformecb":
					System.out.println("Inside searchBy: case LookForMe");
					
					candidateList = getLookingForMe(sessionUser);
					if(candidateList == null) throw new UserDAOException("You Looking_For_Me Search Criteria is not met") ;
					
					break;
				case "ilookfcb":
					System.out.println("Inside searchBy: case whom I am looking for: ");
					candidateList = getIamLookingFor(sessionUser);
					if(candidateList == null) throw new UserDAOException("Your whom_I_am_Looking_For Search Criteria is not met") ;
					
					break;
					
				case "idealcb":
					System.out.println("Inside searchBy: case Ideal: ");
					List<User> lookingForMeList = getLookingForMe(sessionUser);
					List<User> iamLookingForList = getIamLookingFor(sessionUser);
					List<Long> lookingForMeUids = new ArrayList<Long>();
					List<Long> iamLookingForUids = new ArrayList<Long>();;
					for(User user:lookingForMeList) {
						long userId = user.getUserId();
						lookingForMeUids.add(userId);	
					}
					for(User user:iamLookingForList) {
						long userId = user.getUserId();
						iamLookingForUids.add(userId);	
					}
					
					List<Long> common = new ArrayList<Long>(lookingForMeUids);
					common.retainAll(iamLookingForUids);
					System.out.println("uids common generated from lookingForMeUids.retainAll(iamLookingForUids) "+ common.size());
					for(User u:lookingForMeList.size()<iamLookingForList.size()?lookingForMeList:iamLookingForList) {
						if(common.contains(u.getUserId())) candidateList.add(u);
					}
					if(candidateList == null) throw new UserDAOException("Your Ideal Match Search Criteria is not met") ;
					
					break;
					
				case "agel":	// this now works for ageh & agelow
					System.out.println("Inside searchBy case age");
					candidateList = getByAge(sessionUser, v[i+1], v[i+3]);
					if(candidateList == null) throw new UserDAOException("You Search Criteria is not met") ;
					
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new UserDAOException(e.getMessage());
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
		
		return candidateList;
	}
	
	
	String getMatchGender(String gender) {
		String matchGender = "";
		if(gender.equals("M"))	matchGender = "F";
		else	matchGender = "M";
		return matchGender;
	}
	
	List<User> getByLocation(User sessionUser, String desiredLocation){
		List<User> candidateList = new ArrayList<User>();
		String matchGender = getMatchGender(sessionUser.getGender());
		try {
			pstmt = conn.prepareStatement("select * from HL_USERS where gender=? and lookingin=?");
			System.out.println("select * from HL_USERS where gender='"+matchGender+"' and lookingin='"+desiredLocation+"'");
			pstmt.setString(2,desiredLocation);
			pstmt.setString(1,matchGender);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				long userId  = rs.getLong(1);					
				String fname  = rs.getString(2);				
				String lname = rs.getString(3);					
				String emailId  = rs.getString(4);				
				String uname  = rs.getString(5);				
				String pass  = rs.getString(6);			// not taken		
				String gender  = rs.getString(7);				
				String country  = rs.getString(8);				
				String phone  = rs.getString(9);				
				String image  = rs.getString(10);	
				Date dob = rs.getDate(11);
				String residencyStatus = rs.getString(12);
				String aboutMyself = rs.getString(14);
				String lookingFor = rs.getString(15);
				String publicPhoto = rs.getString(16);
				Timestamp lastLogin = rs.getTimestamp(17);
				String profilePostedBy  = rs.getString(18);
				String origin  = rs.getString(19);
				String religiousHistory  = rs.getString(20);
				String hairColor  = rs.getString(21);
				String bodyType  = rs.getString(22);
				String hijabBeard  = rs.getString(24);
				String height = rs.getString(25);
				String pray = rs.getString(26);
				String sect = rs.getString(27);
				String maritalStatus = rs.getString(28);
				String children = rs.getString(29);
				String likeToHaveChildren = rs.getString(30);
				String langs = rs.getString(31);
				String profession = rs.getString(32);
				String highestQual = rs.getString(33);
				
				User candidate = new User(userId, fname, lname, emailId, uname, gender, country, phone, image, dob, 
						residencyStatus, aboutMyself, lookingFor, publicPhoto, lastLogin, profilePostedBy, origin, 
						religiousHistory, hairColor, bodyType, hijabBeard, height, pray, sect, maritalStatus, children, 
						likeToHaveChildren, langs, profession, highestQual);
				candidateList.add(candidate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return candidateList;
	}
	
	Date constructDate(String age) {
		Date dateSql = null;
		if(!age.equalsIgnoreCase("Any"))	{
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int agel = Integer.parseInt(age);
			int yearL = year - agel;
			String dateStr = yearL+"-01-01";
			
			//Date date = Date.valueOf(dateStr);
		}
		return dateSql;
	}
	
	List<User> getByAge(User sessionUser, String agelow, String agehigh) {
		List<User> candidateList = new ArrayList<User>();
		String matchGender = getMatchGender(sessionUser.getGender());
		Date dateLow = constructDate(agelow);
		Date dateHigh = constructDate(agehigh);
		System.out.println("Inside searchBy case dateStr = " + dateLow);
		System.out.println("Inside searchBy case dateStr = " + dateHigh);
		
		try {
			if((dateLow != null) && (dateHigh != null)) { 
				pstmt = conn.prepareStatement("select * from HL_USERS where gender=? and dob between ? and ?");
				pstmt.setDate(2,dateHigh);
				pstmt.setDate(3,dateLow);
				pstmt.setString(1,matchGender);
			}else if(dateLow != null) {
				pstmt = conn.prepareStatement("select * from HL_USERS where gender=? and dob <= ?");
				pstmt.setDate(2,dateLow);
				pstmt.setString(1,matchGender);
			}else if(dateHigh != null) {
				pstmt = conn.prepareStatement("select * from HL_USERS where gender=? and dob >= ?");
				pstmt.setDate(2,dateHigh);
				pstmt.setString(1,matchGender);
			}else  {
				pstmt = conn.prepareStatement("select * from HL_USERS where gender=?");
				pstmt.setString(1,matchGender);
			}
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				long userId  = rs.getLong(1);					
				String fname  = rs.getString(2);				
				String lname = rs.getString(3);					
				String emailId  = rs.getString(4);				
				String uname  = rs.getString(5);				
				String pass  = rs.getString(6);			// not taken		
				String gender  = rs.getString(7);				
				String country  = rs.getString(8);				
				String phone  = rs.getString(9);				
				String image  = rs.getString(10);	
				Date dob = rs.getDate(11);
				String residencyStatus = rs.getString(12);
				String aboutMyself = rs.getString(14);
				String lookingFor = rs.getString(15);
				String publicPhoto = rs.getString(16);
				Timestamp lastLogin = rs.getTimestamp(17);
				String profilePostedBy  = rs.getString(18);
				String origin  = rs.getString(19);
				String religiousHistory  = rs.getString(20);
				String hairColor  = rs.getString(21);
				String bodyType  = rs.getString(22);
				String hijabBeard  = rs.getString(24);
				String height = rs.getString(25);
				String pray = rs.getString(26);
				String sect = rs.getString(27);
				String maritalStatus = rs.getString(28);
				String children = rs.getString(29);
				String likeToHaveChildren = rs.getString(30);
				String langs = rs.getString(31);
				String profession = rs.getString(32);
				String highestQual = rs.getString(33);
				
				User candidate = new User(userId, fname, lname, emailId, uname, gender, country, phone, image, dob, 
						residencyStatus, aboutMyself, lookingFor, publicPhoto, lastLogin, profilePostedBy, origin, 
						religiousHistory, hairColor, bodyType, hijabBeard, height, pray, sect, maritalStatus, children, 
						likeToHaveChildren, langs, profession, highestQual);
				candidateList.add(candidate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return candidateList;
	}
	
	
	List<User> getLookingForMe(User sessionUser) {
		List<User> candidateList = new ArrayList<User>();
		String matchGender = getMatchGender(sessionUser.getGender());
		
		LookingForDAO lfDao = new LookingForDAOImpl(); 
		try {
			List<Long> userIds  = lfDao.getLookingForUserIds(sessionUser);
			if(userIds.size() != 0) {
				DoMath doM = new DoMath();
				String query = doM.constructQuery(userIds, matchGender);
				System.out.println("Inside serchBy() case: looking_for_me, constructed Query from DoMath is: " + query);
				
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				
				while (rs.next()){
					long userId  = rs.getLong(1);					
					String fname  = rs.getString(2);				
					String lname = rs.getString(3);					
					String emailId  = rs.getString(4);				
					String uname  = rs.getString(5);				
					String pass  = rs.getString(6);			// not taken		
					String gender  = rs.getString(7);				
					String country  = rs.getString(8);				
					String phone  = rs.getString(9);				
					String image  = rs.getString(10);	
					Date dob = rs.getDate(11);
					String residencyStatus = rs.getString(12);
					String aboutMyself = rs.getString(14);
					String lookingFor = rs.getString(15);
					String publicPhoto = rs.getString(16);
					Timestamp lastLogin = rs.getTimestamp(17);
					String profilePostedBy  = rs.getString(18);
					String origin  = rs.getString(19);
					String religiousHistory  = rs.getString(20);
					String hairColor  = rs.getString(21);
					String bodyType  = rs.getString(22);
					String hijabBeard  = rs.getString(24);
					String height = rs.getString(25);
					String pray = rs.getString(26);
					String sect = rs.getString(27);
					String maritalStatus = rs.getString(28);
					String children = rs.getString(29);
					String likeToHaveChildren = rs.getString(30);
					String langs = rs.getString(31);
					String profession = rs.getString(32);
					String highestQual = rs.getString(33);
					
					User candidate = new User(userId, fname, lname, emailId, uname, gender, country, phone, image, dob, 
							residencyStatus, aboutMyself, lookingFor, publicPhoto, lastLogin, profilePostedBy, origin, 
							religiousHistory, hairColor, bodyType, hijabBeard, height, pray, sect, maritalStatus, children, 
							likeToHaveChildren, langs, profession, highestQual);
					candidateList.add(candidate);
				}
			}else System.out.println("You Search Criteria is not met");
		} catch (LookingForDAOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return candidateList;
	}
	
	List<User> getIamLookingFor(User sessionUser){
		List<User> candidateList = new ArrayList<User>();
		String matchGender = getMatchGender(sessionUser.getGender());
		
		LookingForDAO lfDao2 = new LookingForDAOImpl(); 
		try {
			LookingFor lf = lfDao2.getLookingForById(sessionUser.getUserId());
			User ilookforUser = new User();
			if(lf.getLookingIn().equalsIgnoreCase("dontmind")) ilookforUser.setLookingIn(null); else ilookforUser.setLookingIn(lf.getLookingIn());
			if(lf.getEthnicOrigin().equalsIgnoreCase("dontmind")) ilookforUser.setEthnicOrigin(null); else ilookforUser.setEthnicOrigin(lf.getEthnicOrigin());
			if(lf.getBodyType().equalsIgnoreCase("dontmind"))ilookforUser.setBodyType(null);	else ilookforUser.setBodyType(lf.getBodyType());
			if(lf.getHijabBeard().equalsIgnoreCase("dontmind")) ilookforUser.setHijabBeard(null); else ilookforUser.setHijabBeard(lf.getHijabBeard());
			if(lf.getPray().equalsIgnoreCase("dontmind")) ilookforUser.setPray(null); else ilookforUser.setPray(lf.getPray());
			if(lf.getSect().equalsIgnoreCase("dontmind")) ilookforUser.setSect(null); else ilookforUser.setSect(lf.getSect());
			if(lf.getMaritalStatus().equalsIgnoreCase("dontmind")) ilookforUser.setMaritalStatus(null); else ilookforUser.setMaritalStatus(lf.getMaritalStatus());
			if(lf.getProfession().equalsIgnoreCase("dontmind")) ilookforUser.setProfession(null); else ilookforUser.setProfession(lf.getProfession());
			// I need to write sql for that
			if(lf.getReligiousHistory().equalsIgnoreCase("dontmind")) ilookforUser.setReligiousHistory(null); else ilookforUser.setReligiousHistory(lf.getReligiousHistory());
			if(lf.getResidencyStatus().equalsIgnoreCase("dontmind")) ilookforUser.setResidencyStatus(null); else ilookforUser.setResidencyStatus(lf.getResidencyStatus());
			if(lf.getHasChildren().equalsIgnoreCase("dontmind")) ilookforUser.setChildren(null); else ilookforUser.setChildren(lf.getHasChildren());
			// I need to add willingtorelocate to user object
			
			DoMath doM = new DoMath();
			String query = doM.constructQuery(ilookforUser, "select * from hl_users where ");
			query = query + " and gender='" +matchGender+ "'";
			pstmt = conn.prepareStatement(query);
			System.out.println("Inside serchBy() case: looking_for_me, constructed Query from DoMath is: " + query);
			rs = pstmt.executeQuery();
			while (rs.next()){
				long userId  = rs.getLong(1);					
				String fname  = rs.getString(2);				
				String lname = rs.getString(3);					
				String emailId  = rs.getString(4);				
				String uname  = rs.getString(5);				
				String pass  = rs.getString(6);			// not taken		
				String gender  = rs.getString(7);				
				String country  = rs.getString(8);				
				String phone  = rs.getString(9);				
				String image  = rs.getString(10);
				Date dob = rs.getDate(11);
				String residencyStatus = rs.getString(12);
				String aboutMyself = rs.getString(14);
				String lookingFor = rs.getString(15);
				String publicPhoto = rs.getString(16);
				Timestamp lastLogin = rs.getTimestamp(17);
				String profilePostedBy  = rs.getString(18);
				String origin  = rs.getString(19);
				String religiousHistory  = rs.getString(20);
				String hairColor  = rs.getString(21);
				String bodyType  = rs.getString(22);
				String hijabBeard  = rs.getString(24);
				String height = rs.getString(25);
				String pray = rs.getString(26);
				String sect = rs.getString(27);
				String maritalStatus = rs.getString(28);
				String children = rs.getString(29);
				String likeToHaveChildren = rs.getString(30);
				String langs = rs.getString(31);
				String profession = rs.getString(32);
				String highestQual = rs.getString(33);
				
				User candidate = new User(userId, fname, lname, emailId, uname, gender, country, phone, image, dob, 
						residencyStatus, aboutMyself, lookingFor, publicPhoto, lastLogin, profilePostedBy, origin, 
						religiousHistory, hairColor, bodyType, hijabBeard, height, pray, sect, maritalStatus, children, 
						likeToHaveChildren, langs, profession, highestQual);
				/*
				 * the candidate fetched from database user table is not checked against age criteria from looking_for table
				 * here is the if condition for that.
				 * condition to check the age may have been added to sql query but the age column is taken off
				 * & the doMath.getAge() has been used instead
				 * another option would be is to do the following to append the sql query 
				 * int year = nowCal.get(Calendar.YEAR);
				 * int yearL = year - Integer.parseInt(lf.getAgeL());	// you need to check that (!lf.getAgeL().equals("Any"))
				 * int yearH = year - Integer.parseInt(lf.getAgeH());	// you need to check that (!lf.getAgeH().equals("Any"))
				 * 
				 * query = query + " and dob BETWEEN '"+yearH+"-01-01' AND '"+yearL+"-12-31'";
				 * */
				int candidage = doM.getAge(candidate.getDob());
				if((lf.getAgeL().equals("Any")) && (lf.getAgeH().equals("Any")))	candidateList.add(candidate);
				else if(!(lf.getAgeL().equals("Any")) && (candidage >= Integer.parseInt(lf.getAgeL())))	{
					if((lf.getAgeH().equals("Any"))) candidateList.add(candidate);
					else if(candidage <= Integer.parseInt(lf.getAgeH())) candidateList.add(candidate);
				}
			}
		} catch (LookingForDAOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return candidateList;
	}
	
	@Override
	public User getUserByUserId(long id) throws UserDAOException {
		User candidate = null;
		try {
			conn = DatabaseConnectivity.doDBConnection();
			pstmt = conn.prepareStatement("select * from HL_USERS where userId=?");
			pstmt.setLong(1,id);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				long userId  = rs.getLong(1);					
				String fname  = rs.getString(2);				
				String lname = rs.getString(3);					
				String emailId  = rs.getString(4);				
				String uname  = rs.getString(5);				
				String pass  = rs.getString(6);					// not needed
				String gender  = rs.getString(7);				
				String country  = rs.getString(8);				
				String phone  = rs.getString(9);				
				String image  = rs.getString(10);				// I should take because I don't need it
				Date dob  = rs.getDate(11);
				String residencyStatus = rs.getString(12);
				String aboutMe = rs.getString(14);
				String lookingfor = rs.getString(15);
				String publicPhoto  = rs.getString(16);
				Timestamp lastLogin = rs.getTimestamp(17);
				String profilePostedBy  = rs.getString(18);
				String origin  = rs.getString(19);
				String religiousHistory  = rs.getString(20);
				String hairColor  = rs.getString(21);
				String bodyType  = rs.getString(22);
				String hijabBeard  = rs.getString(24);
				String height = rs.getString(25);
				String pray = rs.getString(26);
				String sect = rs.getString(27);
				String maritalStatus = rs.getString(28);
				String children = rs.getString(29);
				String likeToHaveChildren = rs.getString(30);
				String langs = rs.getString(31);
				String profession = rs.getString(32);
				String highestQual = rs.getString(33);
				candidate = new User(userId, fname, lname, emailId, uname, gender, country, phone, image, dob, residencyStatus, 
						aboutMe, lookingfor, publicPhoto, lastLogin, profilePostedBy, origin, religiousHistory, hairColor, bodyType,
						hijabBeard, height, pray, sect, maritalStatus, children, likeToHaveChildren, langs, profession, highestQual);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
		return candidate;
	}
	
	@Override
	public User getUserByEmail(String email) throws UserDAOException {
		User candidate = null;
		try {
			conn = DatabaseConnectivity.doDBConnection();
			pstmt = conn.prepareStatement("select * from HL_USERS where Email=?");
			pstmt.setString(1,email);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				long userId  = rs.getLong(1);					
				String fname  = rs.getString(2);				
				String lname = rs.getString(3);					
				String emailId  = rs.getString(4);				
				String uname  = rs.getString(5);				
				String pass  = rs.getString(6);					// not needed
				String gender  = rs.getString(7);				
				String country  = rs.getString(8);				
				String phone  = rs.getString(9);				
				String image  = rs.getString(10);				// I should take because I don't need it
				Date dob  = rs.getDate(11);
				String residencyStatus = rs.getString(12);
				String aboutMe = rs.getString(14);
				String lookingfor = rs.getString(15);
				String publicPhoto  = rs.getString(16);
				Timestamp lastLogin = rs.getTimestamp(17);
				String profilePostedBy  = rs.getString(18);
				String origin  = rs.getString(19);
				String religiousHistory  = rs.getString(20);
				String hairColor  = rs.getString(21);
				String bodyType  = rs.getString(22);
				String hijabBeard  = rs.getString(24);
				String height = rs.getString(25);
				String pray = rs.getString(26);
				String sect = rs.getString(27);
				String maritalStatus = rs.getString(28);
				String children = rs.getString(29);
				String likeToHaveChildren = rs.getString(30);
				String langs = rs.getString(31);
				String profession = rs.getString(32);
				String highestQual = rs.getString(33);
				candidate = new User(userId, fname, lname, emailId, uname, gender, country, phone, image, dob, residencyStatus, 
						aboutMe, lookingfor, publicPhoto, lastLogin, profilePostedBy, origin, religiousHistory, hairColor, bodyType,
						hijabBeard, height, pray, sect, maritalStatus, children, likeToHaveChildren, langs, profession, highestQual);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
		return candidate;
	}
	
	
	// In the v array, the even index represents the key 
	@Override
	public String updateUser(long userId, String... v) throws UserDAOException {
		String msg = "";
		if (v.length %2 != 0) return "wrong number of arguments, number of Arguments must be even";
		try {
			conn = DatabaseConnectivity.doDBConnection();
			for(int i=0 ; i< v.length; i+=2) {
				switch(v[i]) {
				case "Age":
					pstmt = conn.prepareStatement("update HL_USERS set age=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nLanguages Updated Successfully";
					System.out.println(msg);
					break;
				case "AboutMe":
					pstmt = conn.prepareStatement("update HL_USERS set About_Myself=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nLanguages Updated Successfully";
					System.out.println(msg);
					break;
				case "language":
					pstmt = conn.prepareStatement("update HL_USERS set languages=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nLanguages Updated Successfully";
					System.out.println(msg);
					break;
				case "personalphoto":
					pstmt = conn.prepareStatement("update HL_USERS set image=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nPhoto Updated Successfully";
					System.out.println(msg);
					break;
				case "pcreatedby":
					pstmt = conn.prepareStatement("update HL_USERS set profilepostedby=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nProfilePostedBy Updated Successfully";
					System.out.println(msg);
					break;
				case "residencystatus":
					pstmt = conn.prepareStatement("update HL_USERS set residency_status=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nResidency status updated Successfully";
					System.out.println(msg);
					break;
				case "ethnic":
					pstmt = conn.prepareStatement("update HL_USERS set EthnicOrigin=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nEthnic Origin updated Successfully";
					System.out.println(msg);
					break;
				case "rhistory":
					pstmt = conn.prepareStatement("update HL_USERS set ReligiousHistory=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nReligious History updated Successfully";
					System.out.println(msg);
					break;
				case "pray":
					pstmt = conn.prepareStatement("update HL_USERS set Pray=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nPray updated Successfully";
					System.out.println(msg);
					break;
				case "sect":
					pstmt = conn.prepareStatement("update HL_USERS set Sect=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nSect updated Successfully";
					System.out.println(msg);
					break;
				case "mstatus":
					pstmt = conn.prepareStatement("update HL_USERS set MaritalStatus=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nMaritial status updated Successfully";
					System.out.println(msg);
					break;
				case "havekids":
					pstmt = conn.prepareStatement("update HL_USERS set Children=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nChildren updated Successfully";
					System.out.println(msg);
					break;
				case "liketohavekids":
					pstmt = conn.prepareStatement("update HL_USERS set LikeToHaveChildren=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nLikeToHaveChildren updated Successfully";
					System.out.println(msg);
					break;
				case "bodytype":
					pstmt = conn.prepareStatement("update HL_USERS set BodyType=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nBodyType updated Successfully";
					System.out.println(msg);
					break;
				case "haircolor":
					pstmt = conn.prepareStatement("update HL_USERS set HairColor=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nHairColor updated Successfully";
					System.out.println(msg);
					break;
				case "hijaborbeard":
					pstmt = conn.prepareStatement("update HL_USERS set Hijab_Beard=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nBeard updated Successfully";
					System.out.println(msg);
					break;
				case "height":
					pstmt = conn.prepareStatement("update HL_USERS set Height=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nHeight updated Successfully";
					System.out.println(msg);
					break;
				case "profession":
					pstmt = conn.prepareStatement("update HL_USERS set Profession=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nProfession updated Successfully";
					System.out.println(msg);
					break;
				case "hqual":
					pstmt = conn.prepareStatement("update HL_USERS set HighestQual=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nHighestQual updated Successfully";
					System.out.println(msg);
					break;
				case "LookingFor":
					pstmt = conn.prepareStatement("update HL_USERS set looking_for=? where userId=?");
					pstmt.setString(1,v[i+1]);
					pstmt.setLong(2,userId);
					pstmt.executeUpdate();
					msg = msg + "\nLookingFor updated Successfully";
					System.out.println(msg);
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "Something went wrong please try again ! ! !";
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
		return msg;
	}
	
	@Override
	public String updateAboutMe(User user, String aboutme) throws UserDAOException {
		try {
			conn = DatabaseConnectivity.doDBConnection();
			pstmt = conn.prepareStatement("update HL_USERS set about_myself=? where userId=?");
			pstmt.setLong(2,user.getUserId());
			pstmt.setString(1,aboutme);
			pstmt.executeUpdate();
			
			return "About Myself updated Successfully...";
		}catch(Exception e) {
			e.printStackTrace();
			return "Something went wrong please try again ! ! !";
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
	}
	
	@Override
	public String getAboutMe(long userId) throws UserDAOException {
		String aboutmefetched = "";
		try {
			conn = DatabaseConnectivity.doDBConnection();
			PreparedStatement pstmt = conn.prepareStatement("select about_myself from HL_USERS where userId=?");
			pstmt.setLong(1,userId);
			rs = pstmt.executeQuery();			// you have to write code for not creating more than one user with same emailid
			while (rs.next()){
				aboutmefetched  = rs.getString(1);
			}
			
			System.out.println("About me from IMPL is " + aboutmefetched);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
		return aboutmefetched;
	}

	@Override
	public String getLookingFor(long userId) throws UserDAOException {
		String lookingforfetched = "";
		try {
			conn = DatabaseConnectivity.doDBConnection();
			PreparedStatement pstmt = conn.prepareStatement("select looking_for from HL_USERS where userId=?");
			pstmt.setLong(1,userId);
			rs = pstmt.executeQuery();
			while (rs.next()){
				lookingforfetched  = rs.getString(1);
			}
			System.out.println("Looking For from IMPL is " + lookingforfetched);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
		return lookingforfetched;
	}

	@Override
	public String updateLookingFor(User user, String lookingfor) throws UserDAOException {
		try {
			conn = DatabaseConnectivity.doDBConnection();
			pstmt = conn.prepareStatement("update HL_USERS set looking_for=? where userId=?");
			pstmt.setLong(2,user.getUserId());
			pstmt.setString(1,lookingfor);
			pstmt.executeUpdate();
			
			return "Looking For updated Successfully...";
		}catch(Exception e) {
			e.printStackTrace();
			return "Something went wrong please try again ! ! !";
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
	}
	
	@Override
	public String updateUserPhoto(long userId, String photoName) throws UserDAOException {
		try {
			conn = DatabaseConnectivity.doDBConnection();
			pstmt = conn.prepareStatement("update HL_USERS set image=? where userId=?");
			pstmt.setLong(2,userId);
			pstmt.setString(1,photoName);
			pstmt.executeUpdate();
			
			return "Image updated Successfully...";
		}catch(Exception e) {
			e.printStackTrace();
			return "Something went wrong please try again ! ! !";
		}finally {
			try {
				if(conn != null)	conn.close();
				if(pstmt != null)	pstmt.close();
				if(rs != null)		rs.close();
			} catch(Exception e){}
		}
	}
	
	/*
	 * @Override public void setLoginTime(long userId, Timestamp ts) throws
	 * UserDAOException { try { conn = DatabaseConnectivity.doDBConnection(); pstmt
	 * = conn.prepareStatement("update HL_USERS set lastlogin=? where userId=?");
	 * pstmt.setLong(2,userId); pstmt.setTimestamp(1,ts); pstmt.executeUpdate();
	 * 
	 * 
	 * }catch(Exception e) { e.printStackTrace(); }finally { try { if(conn != null)
	 * conn.close(); if(pstmt != null) pstmt.close(); if(rs != null) rs.close(); }
	 * catch(Exception e){} } }
	 */
	
	@Override
	public User doHibernateLogin(String email, String password) throws UserDAOException {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
		Session session = sessionFactory.openSession();
		try{	
			session.beginTransaction();
			List<User> user = session.createQuery("From User where email='"+email+"' and password='"+password+"'").list();
			
			if(user.size() == 1) return user.get(0);
			else throw new UserDAOException("user not found") ;
			
		}catch(Exception e){
			throw new UserDAOException(e.getMessage());
		}finally{
			session.close();
		}
	}

	@Override
	public String doHibernateSignUp(User user) {
		SessionFactory sessionFactory = HibernateConnection.doHibernateConnection();
		Session session = sessionFactory.openSession();
		try{
			System.out.println("Inside doHibernateSignUp");
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			return "Sign Up Successfully...";
			
		}catch(Exception e){
			e.printStackTrace();
			return "User is already there with this username";
		}finally{
			session.close();
		}
	}

}
