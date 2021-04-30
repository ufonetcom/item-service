package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //자동생성자 주입
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //    @PostMapping ("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가가 되기때문에 생략 가능 @ModelAttribute기능

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {
        //@ModelAttribute에 name을 넣지 않으면 받는 클래스명에 앞에 소문자로 붙어서 자동으로 name지정
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가가 되기때문에 생략 가능 @ModelAttribute기능

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item, Model model) {
        //@ModelAttribute를 지워도 객체타입은 자동 으로 @ModelAttribute가 생성 된다. 그리고 클라이언트로 보내주기까지 한다.
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가가 되기때문에 생략 가능 @ModelAttribute기능

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item, Model model) {
        //@ModelAttribute를 지워도 객체타입은 자동 으로 @ModelAttribute가 생성 된다. 그리고 클라이언트로 보내주기까지 한다.
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동 추가가 되기때문에 생략 가능 @ModelAttribute기능

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("iphone", 10000, 10));
        itemRepository.save(new Item("ipad", 20000, 20));
    }

}
