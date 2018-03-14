<?php

/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 *
 * @author Ravi Tamada
 * @link URL Tutorial link
 */
class DbHandler {
    private $conn;
public $action_array=array("LIKE");
    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }
	 function generateID($id,$type){
		 if($type=="customer")
		 return "C-".$id;
		 else return "S-".$id;
	 }
	 function uploadFile ($filename) {
		$target_path = "uploads/";
	// array for final json respone
	$response = array();
		// getting server ip address
		$server_ip = gethostbyname(gethostname());
		 $target=$this->generateApiKey().'.jpg';
		// final file url that is being uploaded
		$file_upload_url = 'http://127.0.0.1/Salon_app/v1/uploads/' . $target;
	if (isset($_FILES[$filename]['name'])) {
   	 $target_path = $target_path .$target;
    // reading other post parameters
    try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES[$filename]['tmp_name'], $target_path)) {
            // make error flag true
           return "";
        }
        // File successfully uploaded
        return $file_upload_url;
    } catch (Exception $e) {
        // Exception occurred. Make error flag true
		return "";    }
		} 
		else {
			// File parameter is missing
		return "";
		}
    }
	
	
	
	
	
function uploadMultifile($filename){
	

	
	
		$target_path = "uploads/";

	// array for final json respone
	$response = array();
	$resp="";
		// getting server ip address
		$server_ip = gethostbyname(gethostname());
		  $total = count($_FILES[$filename]['name']);

	for($i=0; $i<$total; $i++) {	
	 $target=$this->generateApiKey().'.jpg';
		// final file url that is being uploaded
		$file_upload_url = 'http://127.0.0.1/Salon_app/v1/uploads/' . $target;


// Loop through each file

  //Get the temp file path
		 if (isset($_FILES[$filename]['name'][$i])) {
			 $target_path = $target_path .$target;
			// reading other post parameters
    try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES[$filename]['tmp_name'][$i], $target_path)) {
            // make error flag true
           return "";
        }
        // File successfully uploaded
       return $total;
    } catch (Exception $e) {
        // Exception occurred. Make error flag true
		return "";  
		  }
		} 
		else {
			// File parameter is missing
		return "";
		} 
		
}


}

    /* ------------- `users` table method ------------------ */

    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function createUser($name, $email, $password,$phone,$genre,$username) {
        require_once 'PassHash.php';
        $response = array();

        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
						 if (!$this->isSalonExists($email)) {

            // Generating password hash
            $password_hash = PassHash::hash($password);

            // Generating API key
            $api_key = $this->generateApiKey();

            // insert query


            $stmt = $this->conn->prepare("INSERT INTO users(name, email, password_hash, api_key, phone,genre,status,username) values(?, ?, ?, ?, ?, ?, 1, ?)");
            $stmt->bind_param("sssssss", $name, $email, $password_hash, $api_key,$phone,$genre,$username);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        }
		}else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED;
        }

        return $response;
    }
    
    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function UpdateUser($name, $genre,$phone,$photo,$cover,$bio, $email) {
        require_once 'PassHash.php';
        $response = array();

        // First check if user already existed in db

            // Generating API key
            $api_key = $this->generateApiKey();

            // insert query
			$urlimage=$this->uploadFile("photo");	

			$urlimage1=$this->uploadFile("cover");


            $stmt = $this->conn->prepare("UPDATE users SET name=?,status=?,genre=?,phone=?,photo=?,cover=?,bio=? WHERE email=?) values(?, 1, ?, ?, ?, ?, ?, ?)");
            $stmt->bind_param("ssssssss", $name, $genre,$phone,$photo,$urlimage,$urlimage1,$bio, $email);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        
		

        return $response;
    }
    
    /**
     * Creating new salon
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function createSalon($email,$password, $salon_name, $username, $salon_type, $since_from, $phone_number, $country, $city) {
        require_once 'PassHash.php';
        $response = array();

        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
			 if (!$this->isSalonExists($email)) {
            // Generating password hash
            $password_hash = PassHash::hash($password);

            // Generating API key
            $api_key = $this->generateApiKey();
			$urlimage=$this->uploadFile("photo");	

            // insert query
            $stmt = $this->conn->prepare("INSERT INTO salon (email, password,api_key, salon_name,username, salon_type, since_from, phone_number, country, city) values(?,?,?,?,?,?,?,?,?,?)");
        $stmt->bind_param("ssssssssss", $email,$password_hash, $api_key, $salon_name, $username, $salon_type, $since_from, $phone_number, $country, $city);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        } 
		}else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED;
        }

        return $response;
    }
	/**
     * Creating new salon
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function UpdateSalon($email, $salon_name, $username, $salon_type, $since_from, $phone_number, $country, $city,$photo,$latitude,$longutide,$album,$gender,$payment,$work,$branches) {
        require_once 'PassHash.php';
        $response = array();

        // First check if user already existed in db
            // Generating password hash
            // Generating API key

            // insert query
			if(!isset($salon_name))
          {  $stmt = $this->conn->prepare("UPDATE salon SET salon_name = ? WHERE email =?");
       		$stmt->bind_param("ss",$salon_name, $email);
            $result = $stmt->execute();
		  }
		  if(!isset($username))
          {  $stmt = $this->conn->prepare("UPDATE salon SET username = ? WHERE email =?");
       		$stmt->bind_param("ss",$username, $email);
            $result = $stmt->execute();
		  }
		  if(!isset($salon_type))
          {  $stmt = $this->conn->prepare("UPDATE salon SET salon_type = ? WHERE email =?");
       		$stmt->bind_param("ss",$salon_type, $email);
            $result = $stmt->execute();
		  }
		  
		  if(!isset($since_from))
          {  $stmt = $this->conn->prepare("UPDATE salon SET since_from = ? WHERE email =?");
       		$stmt->bind_param("ss",$since_from, $email);
            $result = $stmt->execute();
		  }
		  if(!isset($phone_number))
          {  $stmt = $this->conn->prepare("UPDATE salon SET phone_number = ? WHERE email =?");
       		$stmt->bind_param("ss",$phone_number, $email);
            $result = $stmt->execute();
		  }
		  if(!isset($country))
          {  $stmt = $this->conn->prepare("UPDATE salon SET country = ? WHERE email =?");
       		$stmt->bind_param("ss",$country, $email);
            $result = $stmt->execute();
		  }
		  if(!isset($city))
          {  $stmt = $this->conn->prepare("UPDATE salon SET city = ? WHERE email =?");
       		$stmt->bind_param("ss",$city, $email);
            $result = $stmt->execute();
		  }
		   if(!isset($photo))
          {	
		    $urlimage=$this->uploadFile("photo");	 
		    $stmt = $this->conn->prepare("UPDATE salon SET photo = ? WHERE email =?");
       		$stmt->bind_param("ss",$urlimage, $email);
            $result = $stmt->execute();

		  }
		  if(!isset($latitude))
          {  $stmt = $this->conn->prepare("UPDATE salon SET latitude = ? WHERE email =?");
       		$stmt->bind_param("ss",$latitude, $email);
            $result = $stmt->execute();
		  }
		  
		   if(!isset($album))
          {  $stmt = $this->conn->prepare("UPDATE salon SET photo = ? WHERE email =?");
       		$stmt->bind_param("ss",$photo, $email);
            $result = $stmt->execute();
		  }
		  if(!isset($gender))
          {  $stmt = $this->conn->prepare("UPDATE salon SET latitude = ? WHERE email =?");
       		$stmt->bind_param("ss",$latitude, $email);
            $result = $stmt->execute();
		  }
		   if(!isset($payment))
          {  $stmt = $this->conn->prepare("UPDATE salon SET payment = ? WHERE email =?");
       		$stmt->bind_param("ss",$payment, $email);
            $result = $stmt->execute();
		  }
		  if(!isset($work))
          {  $stmt = $this->conn->prepare("UPDATE salon SET work = ? WHERE email =?");
       		$stmt->bind_param("ss",$work, $email);
            $result = $stmt->execute();
		  }
		  
		   if(!isset($branches))
          {  $stmt = $this->conn->prepare("UPDATE salon SET branches = ? WHERE email =?");
       		$stmt->bind_param("ss",$branches, $email);
            $result = $stmt->execute();
		  }
		 
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        
        return $response;
    }

    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function checkLogin($email, $password) {
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password_hash FROM users WHERE email = ?");

        $stmt->bind_param("s", $email);

        $stmt->execute();

        $stmt->bind_result($password_hash);

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            $stmt->close();

            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                return FALSE;
            }
        } 
			
	else {
            $stmt->close();

            // user not existed with the email
            return FALSE;
        }
    }
public function checkLoginSalon($email, $password) {
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password FROM salon WHERE email = ?");

        $stmt->bind_param("s", $email);

        $stmt->execute();

        $stmt->bind_result($password_hash);

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            $stmt->close();

            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                return FALSE;
            }
        } 
			
	else {
            $stmt->close();

            // user not existed with the email
            return FALSE;
        }
    }

    /**
     * Checking for duplicate user by email address
     * @param String $email email to check in db
     * @return boolean
     */
    private function isUserExists($email) {
        $stmt = $this->conn->prepare("SELECT id from users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	 private function isSalonExists($email) {
        $stmt = $this->conn->prepare("SELECT id from salon WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	 

    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT id,name, email, api_key, status, created_at,phone,genre,photo,cover,bio FROM users WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($id, $name, $email, $api_key, $status, $created_at, $phone, $genre,$photo,$cover,$bio );
            $stmt->fetch();
            $user = array();
			$user["id"] = $id;
            $user["name"] = $name;
            $user["email"] = $email;
            $user["api_key"] = $api_key;
            $user["status"] = $status;
            $user["created_at"] = $created_at;
			$user["phone"] = $phone;
			$user["genre"] = $genre;
			$user["photo"] = $photo;
			$user["cover"] = $cover;
			$user["bio"] = $bio;


            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }


/**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getSalonByEmail($email) {
        $stmt = $this->conn->prepare("SELECT id,email,password,api_key,salon_name,username,salon_type,since_from,phone_number,country,city 
		, photo, latitude, longutide, album, gender, payment, work, branches FROM salon WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result( $id,$email,$password,$api_key, $salon_name, $username, $salon_type, $since_from, $phone_number, $country, $city
			,$photo, $latitude, $longutide, $album, $gender, $payment, $work, $branches);
            $stmt->fetch();
            $salon = array();
			$salon["id"] = $id;
            $salon["email"] = $email;
            $salon['password'] = $password;
			$salon["api_key"] = $api_key;
            $salon['salon_name'] = $salon_name;
            $salon['username'] = $username;
            $salon['salon_type'] = $salon_type;
            $salon['since_from'] = $since_from;
            $salon['phone_number'] = $phone_number;
            $salon['country'] = $country;
            $salon['city'] = $city;  
			$salon['photo'] = $photo;
			$salon['latitude'] = $latitude;
			$salon['longutide'] = $longutide;
			$salon['album'] = $album;
			$salon['gender'] = $gender;
			$salon['payment'] = $payment;
			$salon['work'] = $work;
			$salon['branches'] = $branches;
			$salon["error"] = false;

			$stmt->close();
            return $salon;
        } else {
            return NULL;
        }
    }
	/**
     * Deleting a Account (User OR Salon)
     * @param String $email of the User OR Salon to delete
     */
    public function DeleteAccount($email) {
        $stmt = $this->conn->prepare("DELETE FROM salon WHERE email =?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
		if($num_affected_rows > 0)
        return $num_affected_rows > 0;
		else
		{
		$stmt = $this->conn->prepare("DELETE FROM users WHERE email =?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
		}
		
    }
	
	/**
     * Password updated t (User OR Salon)
     * @param String email, String oldpassword, String newpassword
     */
    public function UpdatePassword($email,$password,$newpassword) {
		 $password_hash = PassHash::hash($newpassword);
		   $password_hash_1 = PassHash::hash($password);
		    $stmt = $this->conn->prepare("UPDATE salon SET password = ? WHERE email =?");
			$stmt->bind_param("ss",$password_hash, $email);
			$stmt->execute();
			$num_affected_rows = $stmt->affected_rows;
			$stmt->close();
			if($num_affected_rows > 0)
			return $num_affected_rows > 0;
			else
			{
			$stmt = $this->conn->prepare("UPDATE users SET password_hash = ? WHERE email =?");
			$stmt->bind_param("ss",$password_hash , $email);
			$stmt->execute();
			$num_affected_rows = $stmt->affected_rows;
			$stmt->close();
			return $num_affected_rows > 0;
		} 
    }
	/**
     * Creating new salon
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function ForgetPassword($email) {
        require_once 'PassHash.php';
  $param = "secret-password-reset-code";

    $mail = new PHPMailer;
$mail->IsSMTP();
$mail->SMTPAuth = true;
$mail->Host = "mail.salon.com.sa";
$mail->Port = 25;
$mail->Username = "salon_repondre@salon.com.sa";
$mail->Password = "50779479";
    $mail->setFrom('salon_repondre@salon.com.sa');
   $mail->AddAddress("marwen.ktata@gmail.com", "marwen");


    $mail->isHTML(true);                                  // Set email format to HTML

    $mail->Subject = 'Instructions for resetting the password for your account with BadgerDating.com';
    $mail->Body    = "
        <p>Hi,</p>
        <p>            
        Thanks for choosing BadgerDating.com!  We have received a request for a password reset on the account associated with this email address.
        </p>
        <p>
        To confirm and reset your password, please click <a href=\"http://badger-dating.com/resetpassword/$id/$param\">here</a>.  If you did not initiate this request,
        please disregard this message.
        </p>
        <p>
        If you have any questions about this email, you may contact us at support@badger-dating.com.
        </p>
        <p>
        With regards,
        <br>
        The BadgerDating.com Team
        </p>";

    if(!$mail->send()) {
        $app->flash("error", "We're having trouble with our mail servers at the moment.  Please try again later, or contact us directly by phone.");
        error_log('Mailer Error: ' . $mail->errorMessage());
        $app->halt(500);
    }     

     
      
        return $response;
    }

    /**
     * Fetching user api key
     * @param String $user_id user id primary key in user table
     */
    public function getApiKeyById($user_id) {
        $stmt = $this->conn->prepare("SELECT api_key FROM users WHERE id = ?");
        $stmt->bind_param("i", $user_id);
        if ($stmt->execute()) {
            // $api_key = $stmt->get_result()->fetch_assoc();
            // TODO
            $stmt->bind_result($api_key);
            $stmt->close();
            return $api_key;
        } else {
            return NULL;
        }
    }

    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
     public function getUserId($api_key) {
        $stmt = $this->conn->prepare("SELECT * FROM users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
		$stmt->execute();
		$resultat=$stmt->get_result();
		$res=$resultat->fetch_assoc();
		if(($resultat->num_rows)>0)
				{	$res['type']='customer';
				return $res;}

		else{
		        $stmt = $this->conn->prepare("SELECT id FROM salon WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
		$stmt->execute();
	$resultat=$stmt->get_result();
		$res=$resultat->fetch_assoc();
				if(($resultat->num_rows)>0)
				{	$res['type']='salon';
				return $res;}
		}
		
    }
    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $api_key user api key
     * @return boolean
     */
    public function isValidApiKey($api_key) {
        $stmt = $this->conn->prepare("SELECT id from users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        if( $num_rows == 0){
			 $stmt = $this->conn->prepare("SELECT id from salon WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
		return($num_rows > 0);
		}
		else
				return($num_rows > 0);

    }

    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function generateApiKey() {
        return md5(uniqid(rand(), true));
    }

    /* ------------- `tasks` table method ------------------ */

    /**
     * Creating new task
     * @param String $user_id user id to whom task belongs to
     * @param String $task task text
     */
    public function createTask($user_id, $task) {
        $stmt = $this->conn->prepare("INSERT INTO tasks(task) VALUES(?)");
        $stmt->bind_param("s", $task);
        $result = $stmt->execute();
        $stmt->close();

        if ($result) {
            // task row created
            // now assign the task to user
            $new_task_id = $this->conn->insert_id;
            $res = $this->createUserTask($user_id, $new_task_id);
            if ($res) {
                // task created successfully
                return $new_task_id;
            } else {
                // task failed to create
                return NULL;
            }
        } else {
            // task failed to create
            return NULL;
        }
    }

    /**
     * Fetching single task
     * @param String $task_id id of the task
     */
    public function getTask($task_id, $user_id) {
        $stmt = $this->conn->prepare("SELECT t.id, t.task, t.status, t.created_at from tasks t, user_tasks ut WHERE t.id = ? AND ut.task_id = t.id AND ut.user_id = ?");
        $stmt->bind_param("ii", $task_id, $user_id);
        if ($stmt->execute()) {
            $res = array();
            $stmt->bind_result($id, $task, $status, $created_at);
            // TODO
            // $task = $stmt->get_result()->fetch_assoc();
            $stmt->fetch();
            $res["id"] = $id;
            $res["task"] = $task;
            $res["status"] = $status;
            $res["created_at"] = $created_at;
            $stmt->close();
            return $res;
        } else {
            return NULL;
        }
    }

    /**
     * Fetching all user tasks
     * @param String $user_id id of the user
     */
    public function getAllUserTasks($user_id) {
        $stmt = $this->conn->prepare("SELECT t.* FROM tasks t, user_tasks ut WHERE t.id = ut.task_id AND ut.user_id = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }

    /**
     * Updating task
     * @param String $task_id id of the task
     * @param String $task task text
     * @param String $status task status
     */
    public function updateTask($user_id, $task_id, $task, $status) {
        $stmt = $this->conn->prepare("UPDATE tasks t, user_tasks ut set t.task = ?, t.status = ? WHERE t.id = ? AND t.id = ut.task_id AND ut.user_id = ?");
        $stmt->bind_param("siii", $task, $status, $task_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }

    /**
     * Deleting a task
     * @param String $task_id id of the task to delete
     */
    public function deleteTask($user_id, $task_id) {
        $stmt = $this->conn->prepare("DELETE t FROM tasks t, user_tasks ut WHERE t.id = ? AND ut.task_id = t.id AND ut.user_id = ?");
        $stmt->bind_param("ii", $task_id, $user_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }

    /* ------------- `user_tasks` table method ------------------ */

    /**
     * Function to assign a task to user
     * @param String $user_id id of the user
     * @param String $task_id id of the task
     */
    public function createUserTask($user_id, $task_id) {
        $stmt = $this->conn->prepare("INSERT INTO user_tasks(user_id, task_id) values(?, ?)");
        $stmt->bind_param("ii", $user_id, $task_id);
        $result = $stmt->execute();

        if (false === $result) {
            die('execute() failed: ' . htmlspecialchars($stmt->error));
        }
        $stmt->close();
        return $result;
    }
/**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function AddPromotion($title,$id_salon,$description,$start_date,$end_date,$price,$discount,$time_service,$client_time,$image) {
        require_once 'PassHash.php';
        $response = array();

            // insert query
            $stmt = $this->conn->prepare("INSERT INTO promotion (title,id_salon,description,start_date,end_date,price,discount,time_service,client_time,image) values(?,?,?,?,?,?,?,?,?,?)");
        $stmt->bind_param("ssssssssss", $title,$id_salon,$description,$start_date,$end_date,$price,$discount,$time_service,$client_time,$image);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }
	 /**
     * Deleting a promotion
     * @param String $promotion_id id of the promotion to delete
     */
    public function deletePromotion($promotion_id) {
        $stmt = $this->conn->prepare("DELETE FROM promotion WHERE id = ?");
        $stmt->bind_param("i", $promotion_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }
	/**
     * Promotion updated User can change all information off an promotion
     */
    public function UpdatePromotion($promotion_id,$title,$id_salon,$description,$start_date,$end_date,$price,$discount,$time_service,$client_time,$image) {
		    $stmt = $this->conn->prepare("UPDATE promotion SET title =?, id_salon =?, description =?, start_date =?, end_date =?,price =?,discount = ?, time_service =?, client_time =?, image =?  WHERE id =?");
			$stmt->bind_param("ssssssssssi", $title,$id_salon,$description,$start_date,$end_date,$price,$discount,$time_service,$client_time,$image,$promotion_id);
			
			$stmt->execute();
			$num_affected_rows = $stmt->affected_rows;
			$stmt->close();
			return $num_affected_rows > 0;	  
    }
	/**
 * Creating new reservation in db
 * method POST
 * params - id_promotion , start_date , end_date , id_custemer
 * url - /AddPromotion/
 */
    public function Reserve($id_promotion , $start_date , $end_date , $id_custemer) {
        require_once 'PassHash.php';
        $response = array();

            // insert query
            $stmt = $this->conn->prepare("INSERT INTO reservation (id_promotion, start_date, end_date, etat, id_custemer) values(?,?,?,'en attent',?)");
        $stmt->bind_param("ssss", $id_promotion,$start_date,$end_date,$id_custemer);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }
/**
     * Promotion updated User can change all information off an promotion
     */
    public function CancelReservation($reservation_id,$cause,$type_user) {
		if($type_user=="Customer")

		 {   $stmt = $this->conn->prepare("UPDATE reservation SET etat = 'cancel' ,cause_custemer = ? WHERE id =? ");
			$stmt->bind_param("ss", $cause,$reservation_id);
			
			$stmt->execute();
			$num_affected_rows = $stmt->affected_rows;
			$stmt->close();
			return $num_affected_rows > 0;	
		 }
		 if($type_user=="Salon")

		 {   $stmt = $this->conn->prepare("UPDATE reservation SET etat = 'cancel_salon' ,cause_salon = ? WHERE id =? ");
			$stmt->bind_param("ss", $cause,$reservation_id);
			
			$stmt->execute();
			$num_affected_rows = $stmt->affected_rows;
			$stmt->close();
			return $num_affected_rows > 0;	
		 }
		 
    }
	/**
     * Promotion updated User can change all information off an promotion
     */
    public function ApproveReservation($reservation_id) {
		    $stmt = $this->conn->prepare("UPDATE reservation SET etat = 'Approved'  WHERE id =? ");
			$stmt->bind_param("s",$reservation_id);
			
			$stmt->execute();
			$num_affected_rows = $stmt->affected_rows;
			$stmt->close();
			return $num_affected_rows > 0;	 
		 
    }
	
	 /**
     * Fetching all user reservation
     * @param String $user_id id of the user
     */
    public function UserReservation($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM reservation WHERE id_custemer= ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
	 /**
     * Fetching all salon	 reservation
     * @param String $user_id id of the user
     */
    public function SalonReservation($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM reservation WHERE id_salon= ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
/**
     * Fetching single promotion
     * @param String $task_id id of the task
     */
    public function getPromotion($promotion_id) {
        $stmt = $this->conn->prepare("SELECT id, title, id_salon, description, posted_date, start_date, end_date, price, discount, time_service, client_time, image from promotion  WHERE id = ?");
        $stmt->bind_param("i", $promotion_id);
        if ($stmt->execute()) {
            $res = array();
            $stmt->bind_result($id, $title, $id_salon, $description, $posted_date, $start_date, $end_date, $price, $discount, $time_service, $client_time, $image);
            // TODO
            // $task = $stmt->get_result()->fetch_assoc();
            $stmt->fetch();
            $res["id"] = $id;
            $res["title"] = $title;
            $res["id_salon"] = $id_salon;
            $res["description"] = $description;
			$res["posted_date"] = $posted_date;
            $res["start_date"] = $start_date;
            $res["end_date"] = $end_date;
            $res["price"] = $price;
            $res["discount"] = $discount;
            $res["time_service"] = $time_service;
            $res["client_time"] = $client_time;
            $res["image"] = $image;

            $stmt->close();
            return $res;
        } else {
            return NULL;
        }
    }
/**
     * Fetching all comment for one	 promotion
     * @param String $user_id id of the user
     */
    public function CommentPromotion($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM comment WHERE type='promotion' AND id_object = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
    public function CommentService($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM comment WHERE type='service' AND id_object = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
	/**
     * Fetching all like for one promotion
     * @param String $user_id id of the user
     */
    public function LikePromotion($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM user_like WHERE type='promotion' AND id_object = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
    public function LikeService($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM user_like WHERE type='service' AND id_object = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }

	/**
     * Fetching all favorit for one promotion
     * @param String $user_id id of the user
     */
 public function FavoritPromotion($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM favorit WHERE type='promotion' AND id_object = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
    public function FavoritService($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM favorit WHERE type='service' AND id_object = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }


    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getUser($id,$type) {
		if($type=="customer"){
       $stmt = $this->conn->prepare("SELECT id, name, email, api_key, status, created_at,phone,genre,photo,cover,bio FROM users WHERE id = ?");
        $stmt->bind_param("s", $id);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($id, $name, $email, $api_key, $status, $created_at, $phone, $genre,$photo,$cover,$bio);
            $stmt->fetch();
            $user = array();
			$user["id"] = $id;
            $user["name"] = $name;
            $user["email"] = $email;
            $user["api_key"] = $api_key;
            $user["status"] = $status;
            $user["created_at"] = $created_at;
			$user["phone"] = $phone;
			$user["genre"] = $genre;
			$user["photo"] = $photo;
			$user["cover"] = $cover;
			$user["bio"] = $bio;


            $stmt->close();
            return $user;
        }
		} else 
		
		if($type=="salon"){		
		 $stmt = $this->conn->prepare("SELECT email,password,api_key,salon_name,username,salon_type,since_from,phone_number,country,city FROM salon WHERE id = ?");
        $stmt->bind_param("s", $id);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result( $email,$password,$api_key, $salon_name, $username, $salon_type, $since_from, $phone_number, $country, $city);
            $stmt->fetch();
            $salon = array();
			$salon["id"] = "1";
            $salon["email"] = $email;
            $salon['password'] = $password;
			$salon["api_key"] = $api_key;
            $salon['name'] = $salon_name;
            $salon['username'] = $username;
            $salon['salon_type'] = $salon_type;
            $salon['since_from'] = $since_from;
            $salon['phone_number'] = $phone_number;
            $salon['country'] = $country;
            $salon['city'] = $city;     
			$salon["error"] = false;

			$stmt->close();
            return $salon;
        } else {
            return NULL;
        }
		
	}
    }
	/**
 * Creating new comment in db
 * method POST
 * params - id_object, id_user, type, type_user, contenu
 * url - /AddComment/
 */
    public function AddComment($id_object, $id_user, $type, $type_user, $contenu) {
        require_once 'PassHash.php';
        $response = array();

            // insert query
            $stmt = $this->conn->prepare("INSERT INTO comment (id_object,id_user,type,type_user,contenu) values(?,?,?,?,?)");
        $stmt->bind_param("iisss", $id_object, $id_user, $type, $type_user, $contenu);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }
/**
 * Creating new like if not existe with the same data else delete the like in db
 * method POST
 * params - id_object, id_user, type, type_user, contenu
 * url - /AddComment/
 */
 
    public function Addlike($id_object, $id_user, $type, $type_user) {
        require_once 'PassHash.php';
        $response = array();
        $stmt = $this->conn->prepare("SELECT * FROM user_like WHERE type='promotion' AND id_object = ? AND type_user=? AND id_user=?");
        $stmt->bind_param("sss", $id_object,$type_user,$id_user);
        $stmt->execute();
 $stmt->store_result();
     $this->timeLine($id_user,$type_user,$id_object,$type,1,"5555");
        if ($stmt->num_rows == 0)             // insert query
      {   
	     $stmt = $this->conn->prepare("INSERT INTO user_like (id_object,id_user,type,type_user) values(?,?,?,?)");
        $stmt->bind_param("iiss", $id_object, $id_user, $type, $type_user);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return like_added_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
	  }
	  else{
		   $stmt = $this->conn->prepare("DELETE FROM `user_like` WHERE type='promotion' AND id_object = ? AND type_user=? ");
        $stmt->bind_param("is", $id_object, $type_user);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return like_delete_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
		  
	  }
    }
/**
 * Creating new favorit if not existe with the same data else delete the like in db
 * method POST
 * params - id_object, id_user, type, type_user, contenu
 * url - /AddComment/
 */
    public function Addfavorit($id_object, $id_user, $type, $type_user) {
        require_once 'PassHash.php';
        $response = array();
        $stmt = $this->conn->prepare("SELECT * FROM favorit WHERE type='promotion' AND id_object = ? AND type_user=? AND id_user=?");
        $stmt->bind_param("sss", $id_object,$type_user,$id_user);
        $stmt->execute();
 $stmt->store_result();

        if ($stmt->num_rows == 0)             // insert query
      {   
	     $stmt = $this->conn->prepare("INSERT INTO favorit (id_object,id_user,type,type_user) values(?,?,?,?)");
        $stmt->bind_param("iiss", $id_object, $id_user, $type, $type_user);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return like_added_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
	  }
	  else{
		   $stmt = $this->conn->prepare("DELETE FROM `favorit` WHERE type='promotion' AND id_object = ? AND type_user=? ");
        $stmt->bind_param("is", $id_object, $type_user);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return like_delete_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
		  
	  }
    }
	/**
     * Fetching all salons 
     */
    public function salons() {
        $stmt = $this->conn->prepare("SELECT * FROM salon");
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
	/**
     * Fetching  salons by id
     */
    public function SalonDetail($id_object) {
        $stmt = $this->conn->prepare("SELECT * FROM salon WHERE id=?");
		 $stmt->bind_param("i", $id_object);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
	/**
     * Fetching  service salons by id
     */
    public function SalonDetail_service($id_object) {
        $stmt = $this->conn->prepare("SELECT * FROM service WHERE id_salon=?");
		 $stmt->bind_param("i", $id_object);
        $stmt->execute();
		 $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
		   
	
	}
	/**
     * Fetching  price salons by id
     */
    public function SalonDetail_price($id_object) {
        $stmt = $this->conn->prepare("SELECT * FROM  price WHERE id_salon=?");
		 $stmt->bind_param("i", $id_object);
        $stmt->execute();
		 $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
	}
	
	/**
     * Fetching  promotion salons by id
     */
    public function SalonDetail_promotion($id_object) {
        $stmt = $this->conn->prepare("SELECT * FROM  promotion WHERE id_salon=?");
		 $stmt->bind_param("i", $id_object);
        $stmt->execute();
		 $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
	}
	
/**
     * Fetching  promotion salons by id
     */
    public function users() {
        $stmt = $this->conn->prepare("SELECT * FROM  users");
        $stmt->execute();
		$tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
	}
	



























































/***************marweeeennnnnnnnnnnnnnnnnnnnn**************************/
/***************** categoy servisz */
 public function AddCatSevice($name,$id_salon,$image) {
        require_once 'PassHash.php';
        $response = array();
$urlimage=$this->uploadFile("image");
            // insert query
            $stmt = $this->conn->prepare("INSERT INTO servicecategory (name,id_salon,image_url) values(?,?,?)");
        $stmt->bind_param("sss", $name,$id_salon,$urlimage);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }
	/***************** categoy servisz */
 public function AddSevice($name,$id_salon,$idcat,$image,$description) {
        require_once 'PassHash.php';
        $response = array();
$urlimage=$this->uploadFile("image");
            // insert query
            $stmt = $this->conn->prepare("INSERT INTO service (name,id_salon,category_id,image_url,description) values(?,?,?,?,?)");
        $stmt->bind_param("sssss", $name,$id_salon,$idcat,$urlimage,$description);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }
	
	
	 public function UpdateSevice($name,$idcat,$image,$description,$idser) {
        require_once 'PassHash.php';
        $response = array();

           $urlimage=$this->uploadFile("image");
    if (!$urlimage=="") {

		   $stmt = $this->conn->prepare("UPDATE service set name = ? ,category_id=?,description=?,image_url=? WHERE id = ?");
        $stmt->bind_param("ssssi",$name,$idcat,$description, $urlimage,$idser);
        $stmt->execute();
		        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows < 0;   
   }
   else
{ 		   $stmt = $this->conn->prepare("UPDATE service set name = ? ,category_id=?,description=? WHERE id = ?");
        $stmt->bind_param("sssi",$name,$idcat,$description,$idser);

        $stmt->execute();
		        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
}
    }


 public function UpdatePrice($name,$idcat,$image,$description,$max_price,$min_price,$currency,$idprice) {
        require_once 'PassHash.php';
        $response = array();

           $urlimage=$this->uploadFile("image");
    if (!$urlimage=="") {

		   $stmt = $this->conn->prepare("UPDATE price set name = ? ,id_category=?,description=?,image_url=?,currency=?,max_price=?,min_price=? WHERE id = ?");
        $stmt->bind_param("sssssssi",$name,$idcat,$description, $urlimage,$currency,$max_price,$min_price,$idprice);
        $stmt->execute();
		        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;   
   }
   else{
		   $stmt = $this->conn->prepare("UPDATE price set name = ? ,id_category=?,description=?,currency=?,max_price=?,min_price=? WHERE id = ?");
        $stmt->bind_param("ssssssi",$name,$idcat,$description,$currency,$max_price,$min_price,$idprice);

        $stmt->execute();
		        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows < 0;
}
    }


	/*****************  price */
 public function AddPrice($name,$id_salon,$idcat,$image,$description,$max_price,$min_price,$currency) {
        require_once 'PassHash.php';
        $response = array();
$urlimage=$this->uploadFile("image");
            // insert query
            $stmt = $this->conn->prepare("INSERT INTO price (name,id_salon,id_category,image_url,description,currency,max_price,min_price) values(?,?,?,?,?,?,?,?)");
        $stmt->bind_param("ssssssss", $name,$id_salon,$idcat,$urlimage,$description,$currency,$max_price,$min_price);

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }
 public function deleteCatService($service_id) {
        $stmt = $this->conn->prepare("DELETE FROM `servicecategory` WHERE `id` = ?");
        $stmt->bind_param("i", $service_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }
	 public function deleteService($service_id) {
        $stmt = $this->conn->prepare("DELETE FROM `service` WHERE `id` = ?");
        $stmt->bind_param("i", $service_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }
		 public function deleteprice($service_id) {
        $stmt = $this->conn->prepare("DELETE FROM `price` WHERE `id` = ?");
        $stmt->bind_param("i", $service_id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }
    public function getAllUserCat($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM servicecategory WHERE id_salon = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
    public function getAllUserService($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM service WHERE id_salon = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
  public function getService($id) {
        $stmt = $this->conn->prepare("SELECT * FROM service WHERE id = ?");
        $stmt->bind_param("i", $id);



      if ($stmt->execute()) {
          $res = array();
          $stmt->bind_result($id, $category_id, $image_url, $name, $description, $id_salon);
          // TODO
          // $task = $stmt->get_result()->fetch_assoc();
          $stmt->fetch();
          $res["id"] = $id;
          $res["name"] = $name;
          $res["id_salon"] = $id_salon;
          $res["description"] = $description;
          $res["image_url"] = $image_url;
          $res["category_id"] = $category_id;

          $stmt->close();
          return $res;
      } else {
          return NULL;
      }


    }
    public function getAllUserPrice($user_id) {
        $stmt = $this->conn->prepare("SELECT * FROM price WHERE id_salon = ?");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
	
public function UpDateCatSevice($title,$image,$id_cat) {
	 $urlimage=$this->uploadFile("image");
    if (!$urlimage=="") {

		   $stmt = $this->conn->prepare("UPDATE servicecategory set image_url =?,name=? WHERE id = ?");
        $stmt->bind_param("sss",$urlimage,$title, $id_cat);
        $stmt->execute();
		        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows < 0;   
   }
   else
{ $stmt = $this->conn->prepare("UPDATE servicecategory set name=? WHERE id = ?");
        $stmt->bind_param("si",$title, $id_cat);
        $stmt->execute();
		        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
}
}












/** ++++++++++++++++ADD block +++++++++++*/
 public function AddBlock($id_user,$type_user,$id_blocked,$type_blocked) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
	$id1=	$this->generateID($id_user,$type_user);
	$id2=$this->generateID($id_blocked,$type_blocked);
            $stmt = $this->conn->prepare("INSERT INTO liste_block (id_user,id_blocked) values(?,?)");
        $stmt->bind_param("ss",$id1,$id2 );

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }

/** ++++++++++++++++My block +++++++++++*/
 public function MyBlock($id_user,$type_user) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
	$id1=	$this->generateID($id_user,$type_user);
            $stmt = $this->conn->prepare("SELECT * FROM liste_block WHERE id_user =?");
        $stmt->bind_param("s",$id1);

             $stmt->execute();

                    $tasks = $stmt->get_result();

                     $stmt->close();

        return $tasks;
    }
function get_user_b_a($id_user) {
        require_once 'PassHash.php';
        $response = array();

	$ch=explode('-', $id_user);
	if($ch[0]=="S"){
            $stmt = $this->conn->prepare("SELECT * FROM salon WHERE id =?");
        $stmt->bind_param("s",$ch[1]);

            $stmt->execute();
								            $res = $stmt->get_result();



            while ($tasks = $res->fetch_assoc()) {


                     $stmt->close();
                $tmp["id_user"] = $tasks["id"];
                $tmp["name"] = $tasks["salon_name"];
                $tmp["image_url"] = $tasks["photo"];
							  	
				$tmp["user_type"]="salon";
				}
        return $tmp;
    }
	else{
		      $stmt = $this->conn->prepare("SELECT * FROM users WHERE id =?");
        $stmt->bind_param("s",$ch[1]);

            $stmt->execute();
			                    $res = $stmt->get_result();

            while ($tasks = $res->fetch_assoc()) {


                     $stmt->close();
                $tmp["id_user"] = $tasks["id"];
                $tmp["name"] = $tasks["name"];
                $tmp["image_url"] = $tasks["photo"];
				$tmp["user_type"]="user";
            }
        return $tmp;
		
		
	}
	}
	 public function AddFLow($id_user,$type_user,$id_flow,$type_flow) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
	$id1=	$this->generateID($id_user,$type_user);
	$id2=$this->generateID($id_flow,$type_flow);
            $stmt = $this->conn->prepare("INSERT INTO following (id_user,id_flow) values(?,?)");
        $stmt->bind_param("ss",$id1,$id2 );

            $result = $stmt->execute();
            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
         
        return $response;
    }

	 public function MyFlow($id_user,$type_user) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
	$id1=	$this->generateID($id_user,$type_user);
            $stmt = $this->conn->prepare("SELECT * FROM following WHERE id_user =?");
        $stmt->bind_param("s",$id1);

             $stmt->execute();

                    $tasks = $stmt->get_result();

                     $stmt->close();

        return $tasks;
    }
	 public function MyFlowMe($id_user,$type_user) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
	$id1=	$this->generateID($id_user,$type_user);
            $stmt = $this->conn->prepare("SELECT * FROM following WHERE id_flow =?");
        $stmt->bind_param("s",$id1);

             $stmt->execute();

                    $tasks = $stmt->get_result();

                     $stmt->close();

        return $tasks;
    }
	 public function MyFavorite($id_user,$type_user) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
            $stmt = $this->conn->prepare("SELECT * FROM favorit WHERE id_user =? AND type_user=?");
        $stmt->bind_param("ss",$id_user,$type_user);

             $stmt->execute();

                    $tasks = $stmt->get_result();

                     $stmt->close();

        return $tasks;
    }
 public function deletefolow($id){
	 

        $stmt = $this->conn->prepare("DELETE FROM `following` WHERE `id` = ? ");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }
	 public function deleteblock($id){
	 

        $stmt = $this->conn->prepare("DELETE FROM `liste_block` WHERE `id` = ? ");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
        $stmt->close();
        return $num_affected_rows > 0;
    }





	
	
	 public function VerifUserNameC($name) {
        $stmt = $this->conn->prepare("SELECT * FROM `users` WHERE `username`=?");
        $stmt->bind_param("s", $name);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }
		 public function VerifUserNameS($name) {
        $stmt = $this->conn->prepare("SELECT * FROM `salon` WHERE `username`=?");
        $stmt->bind_param("s", $name);
        $stmt->execute();
        $tasks = $stmt->get_result();
        $stmt->close();
        return $tasks;
    }

	 public function ISFLow($id_user,$type_user,$id_flow,$type_flow) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
	$id1=	$this->generateID($id_user,$type_user);
	$id2=$this->generateID($id_flow,$type_flow);
            $stmt = $this->conn->prepare("SELECT * FROM following WHERE id_user=? AND id_flow=?");
        $stmt->bind_param("ss",$id1,$id2 );

           $stmt->execute();
                    $tasks = $stmt->get_result();

         
        return ($tasks->num_rows)>0;
    }
public function Rating_salon($id_salon) {
        require_once 'PassHash.php';
        $response = array();
            // insert query
            $stmt = $this->conn->prepare("SELECT AVG(`stars`) AS stars FROM `rating` WHERE `id_salon` =?");
        $stmt->bind_param("s",$id_salon);

             $stmt->execute();

                    $tasks = $stmt->get_result();

                     $stmt->close();

        return $tasks;
    }
	
	 public function timeLine($id_user,$type_user,$id_objet,$type_object,$action,$message) {
        require_once 'PassHash.php';
        $response = array();

        // First check if user already existed in db
      	$id1=	$this->generateID($id_user,$type_user);

            // insert query
            $stmt = $this->conn->prepare("INSERT INTO `timeline` (`id_user`, `id_objet`, `type_object`, `action`, `message`)  values(?,?,?,?,?)");
        $stmt->bind_param("sssss", $id1,$id_objet,$type_object,$action,$message);

            $result = $stmt->execute();
            $stmt->close();

            
    }
 

	
	public function getAction($id_user,$type_user){
		    require_once 'PassHash.php';
        $response = array();
		$useraray=array();
     $myflow=$this->MyFlow($id_user,$type_user);
		            while ($task = $myflow->fetch_assoc()) {
						array_push($useraray,$task['id_flow']);

					}
                       $ids = join("','",$useraray);   

					 $stmt = $this->conn->prepare("SELECT * FROM `timeline` WHERE `id_user` IN (?)");
        $stmt->bind_param("s",$ids);

             $stmt->execute();

                    $tasks = $stmt->get_result();

                     $stmt->close();
					 return $tasks;
					
		}









































}

?>
