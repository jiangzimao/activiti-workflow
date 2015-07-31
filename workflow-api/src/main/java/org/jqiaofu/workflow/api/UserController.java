/**
 * workflow-api
 * org.jqiaofu.workflow.api
 * UserController.java
 * 
 * 2015年7月30日-下午1:14:39
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * UserController
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月30日 下午1:14:39
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/userInfo")
	@ResponseBody
	public Map<String, Object> getUserInof(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userName", "张三");
		return result;
	}
}
