package com.nowcoder.community.controller;

import com.mysql.cj.log.Log;
import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LikeService likeService;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }


    // The method for uploading images should be POST
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "You must select an image to upload.");
            return "/site/setting";
        }

        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".")); // get the file extension
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "The file format is not supported.");
            return "/site/setting";
        }

        // Generate a random file name
        filename = CommunityUtil.generateUUID() + suffix;

        // Determine the destination path
        File dest = new File(uploadPath + "/" + filename);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("Failed to upload file: " + e.getMessage());
            throw new RuntimeException("Failed to upload file.", e);
        }

        // Update the current user's headerUrl (web path)
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{filename}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response) {
        // the destination path in the server
        filename = uploadPath + "/" + filename;
        // get the file suffix
        String suffix = filename.substring(filename.lastIndexOf("."));
        // response image
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(filename);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("Failed to get image: " + e.getMessage());
        }

    }

    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword, String confirmPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword, confirmPassword);
        if (map == null || map.isEmpty()) {
            model.addAttribute("successMsg", "Password updated successfully.");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        }else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            model.addAttribute("confirmPasswordMsg", map.get("confirmPasswordMsg"));
            return "/site/setting";
        }
    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("The user does not exist!");
        }

        // 用户
        model.addAttribute("user", user);
        // 点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

//        // 关注数量
//        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
//        model.addAttribute("followeeCount", followeeCount);
//        // 粉丝数量
//        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
//        model.addAttribute("followerCount", followerCount);
//        // 是否已关注
//        boolean hasFollowed = false;
//        if (hostHolder.getUser() != null) {
//            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
//        }
//        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }

}
