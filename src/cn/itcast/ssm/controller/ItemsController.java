package cn.itcast.ssm.controller;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.controller.validation.ValidGroup1;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;


/**
 * 
 * <p>
 * Title: ItemsController
 * </p>
 * <p>
 * Description:鍟嗗搧绠＄悊
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 浼犳櫤.鐕曢潚
 * @date 2015-3-20涓嬪崍3:04:57
 * @version 1.0
 */
@Controller
//瀹氫箟url鐨勬牴璺緞锛岃闂椂鏍硅矾寰�+鏂规硶鐨剈rl
@RequestMapping("/items")
public class ItemsController {

	//娉ㄥ叆service
	@Autowired
	private ItemsService itemsService;
	
	//鍗曠嫭灏嗗晢鍝佺被鍨嬬殑鏂规硶鎻愬嚭鏉ワ紝灏嗘柟娉曡繑鍥炲�煎～鍏呭埌request锛屽湪椤甸潰鏄剧ず
	@ModelAttribute("itemsType")
	public Map<String, String> getItemsType()throws Exception{
		
		HashMap<String, String> itemsType = new HashMap<String,String>();
		itemsType.put("001", "鏁扮爜");
		itemsType.put("002", "鏈嶈");
		return itemsType;
		
	}
	//鍟嗗搧淇℃伅鏂规硶
	@RequestMapping("/queryItems")
	@RequiresPermissions("item:query")//鎵цqueryItems闇�瑕�"item:query"鏉冮檺
	public ModelAndView queryItems(HttpServletRequest request) throws Exception {
		
		System.out.println(request.getParameter("id"));
	
		//璋冪敤service鏌ヨ鍟嗗搧鍒楄〃
		List<ItemsCustom> itemsList = itemsService.findItemsList(null);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList", itemsList);
		// 鎸囧畾閫昏緫瑙嗗浘鍚�
		modelAndView.setViewName("itemsList");

		return modelAndView;
	}
	//鎵归噺淇敼鍟嗗搧鏌ヨ
	@RequestMapping("/editItemsList")
	public ModelAndView editItemsList(HttpServletRequest request) throws Exception {
		
		System.out.println(request.getParameter("id"));
	
		//璋冪敤service鏌ヨ鍟嗗搧鍒楄〃
		List<ItemsCustom> itemsList = itemsService.findItemsList(null);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList", itemsList);
		// 鎸囧畾閫昏緫瑙嗗浘鍚�
		modelAndView.setViewName("editItemsList");

		return modelAndView;
	}
	//鎵归噺淇敼鍟嗗搧鎻愪氦
	@RequestMapping("/editItemsListSubmit")
	public String editItemsListSubmit(ItemsQueryVo itemsQueryVo)throws Exception{
		
		return "success";
	}
	
	//鍟嗗搧淇敼椤甸潰鏄剧ず
	//浣跨敤method=RequestMethod.GET闄愬埗浣跨敤get鏂规硶
//	@RequestMapping(value="/editItems",method={RequestMethod.GET})
//	public ModelAndView editItems()throws Exception{
//		
//		ModelAndView modelAndView = new ModelAndView();
//		
//		//璋冪敤 service鏌ヨ鍟嗗搧淇℃伅
//		ItemsCustom itemsCustom = itemsService.findItemsById(1);
//		//灏嗘ā鍨嬫暟鎹紶鍒癹sp
//		modelAndView.addObject("item", itemsCustom);
//		//鎸囧畾閫昏緫瑙嗗浘鍚�
//		modelAndView.setViewName("editItem");
//		
//		return modelAndView;
//		
//	}
	
	//鏂规硶杩斿洖 瀛楃涓诧紝瀛楃涓插氨鏄�昏緫瑙嗗浘鍚嶏紝Model浣滅敤鏄皢鏁版嵁濉厖鍒皉equest鍩燂紝鍦ㄩ〉闈㈠睍绀�
	@RequestMapping(value="/editItems",method={RequestMethod.GET})
	@RequiresPermissions("item:update")//鎵ц姝ゆ柟娉曢渶瑕�"item:update"鏉冮檺 
	public String editItems(Model model,Integer id)throws Exception{
		
		//灏唅d浼犲埌椤甸潰
		model.addAttribute("id", id);
		
		//璋冪敤 service鏌ヨ鍟嗗搧淇℃伅
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
				
		model.addAttribute("itemsCustom", itemsCustom);

		//return "editItem_2";
		return "editItem";
		
	}
	//鏍规嵁鍟嗗搧id鏌ョ湅鍟嗗搧淇℃伅rest鎺ュ彛
	//@RequestMapping涓寚瀹歳estful鏂瑰紡鐨剈rl涓殑鍙傛暟锛屽弬鏁伴渶瑕佺敤{}鍖呰捣鏉�
	//@PathVariable灏唘rl涓殑{}鍖呰捣鍙傛暟鍜屽舰鍙傝繘琛岀粦瀹�
	@RequestMapping("/viewItems/{id}")
	public @ResponseBody ItemsCustom viewItems(@PathVariable("id") Integer id) throws Exception{
		//璋冪敤 service鏌ヨ鍟嗗搧淇℃伅
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		
		return itemsCustom;
		
	}
	
	//鏂规硶杩斿洖void
//	@RequestMapping(value="/editItems",method={RequestMethod.GET})
//	public void editItems(
//			HttpServletRequest request,
//			HttpServletResponse response,
//			//@RequestParam(value = "item_id", required = false, defaultValue = "1") Integer id
//			Integer id
//			)
//			throws Exception {
//	
//		//璋冪敤 service鏌ヨ鍟嗗搧淇℃伅
//		ItemsCustom itemsCustom = itemsService.findItemsById(id);
//		request.setAttribute("item", itemsCustom);
//		//娉ㄦ剰濡傛灉浣跨敤request杞悜椤甸潰锛岃繖閲屾寚瀹氶〉闈㈢殑瀹屾暣璺緞
//		request.getRequestDispatcher("/WEB-INF/jsp/editItem.jsp").forward(request, response);
//		
//	}
	
	//鍟嗗搧淇敼鎻愪氦
	
	//itemsQueryVo鏄寘瑁呯被鍨嬬殑pojo
	//鍦ˊValidated涓畾涔変娇鐢╒alidGroup1缁勪笅杈圭殑鏍￠獙
	@RequestMapping("/editItemSubmit")
//	public String editItemSubmit(Integer id,ItemsCustom itemsCustom,
//			ItemsQueryVo itemsQueryVo)throws Exception{
	@RequiresPermissions("item:update")//鎵ц姝ゆ柟娉曢渶瑕�"item:update"鏉冮檺 
	public String editItemSubmit(Model model,Integer id,
				@Validated(value={ValidGroup1.class}) @ModelAttribute(value="itemsCustom") ItemsCustom itemsCustom,
				BindingResult bindingResult,
			//涓婁紶鍥剧墖
			MultipartFile pictureFile
			)throws Exception{
		
		//杈撳嚭鏍￠獙閿欒淇℃伅
		//濡傛灉鍙傛暟缁戝畾鏃舵湁閿�
		if(bindingResult.hasErrors()){
			
			//鑾峰彇閿欒 
			List<ObjectError> errors = bindingResult.getAllErrors();
			//鍑嗗鍦ㄩ〉闈㈣緭鍑篹rrors锛岄〉闈娇鐢╦stl閬嶅巻
			model.addAttribute("errors", errors);
			for(ObjectError error:errors){
				//杈撳嚭閿欒淇℃伅
				System.out.println(error.getDefaultMessage());
			}
			//濡傛灉鏍￠獙閿欒锛屽洖鍒板晢鍝佷慨鏀归〉闈�
			return "editItem";
		}
		
		//杩涜鏁版嵁鍥炴樉
		model.addAttribute("id", id);
		//model.addAttribute("item", itemsCustom);
		//杩涜鍥剧墖涓婁紶
		if(pictureFile!=null && pictureFile.getOriginalFilename()!=null && pictureFile.getOriginalFilename().length()>0){
			//鍥剧墖涓婁紶鎴愬姛鍚庯紝灏嗗浘鐗囩殑鍦板潃鍐欏埌鏁版嵁搴�
			String filePath = "F:\\develop\\upload\\temp\\";
			//涓婁紶鏂囦欢鍘熷鍚嶇О
			String originalFilename = pictureFile.getOriginalFilename();
			//鏂扮殑鍥剧墖鍚嶇О
			String newFileName = UUID.randomUUID() +originalFilename.substring(originalFilename.lastIndexOf("."));
			//鏂版枃浠�
			File file = new java.io.File(filePath+newFileName);
			
			//灏嗗唴瀛樹腑鐨勬枃浠跺啓鍏ョ鐩�
			pictureFile.transferTo(file);
			
			//鍥剧墖涓婁紶鎴愬姛锛屽皢鏂板浘鐗囧湴鍧�鍐欏叆鏁版嵁搴�
			itemsCustom.setPic(newFileName);
		}
		
		//璋冪敤service鎺ュ彛鏇存柊鍟嗗搧淇℃伅
		itemsService.updateItems(id, itemsCustom);
		
		//鎻愪氦鍚庡洖鍒颁慨鏀归〉闈�
		//return "editItem";
		//璇锋眰閲嶅畾鍚�
		return "redirect:queryItems.action";
		//杞彂
//		return "forward:queryItems.action";
	}
	
	//鍒犻櫎 鍟嗗搧
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] delete_id)throws Exception{
		
		//璋冪敤service鏂规硶鍒犻櫎 鍟嗗搧
		//....
		
		return "success";
	}
	
	//鑷畾涔夊睘鎬х紪杈戝櫒
//	@InitBinder
//	public void initBinder(WebDataBinder binder) throws Exception {
//		// Date.class蹇呴』鏄笌controler鏂规硶褰㈠弬pojo灞炴�т竴鑷寸殑date绫诲瀷锛岃繖閲屾槸java.util.Date
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(
//				new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"), true));
//	}

	

}
