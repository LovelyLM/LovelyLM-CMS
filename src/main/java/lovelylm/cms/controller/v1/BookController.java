package lovelylm.cms.controller.v1;

import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.Logger;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import lovelylm.cms.dto.book.CreateOrUpdateBookDTO;
import lovelylm.cms.model.BookDO;
import lovelylm.cms.service.BookService;
import lovelylm.cms.vo.CreatedVO;
import lovelylm.cms.vo.DeletedVO;
import lovelylm.cms.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author lovelylm
 */
@RestController
@RequestMapping("/v1/book")
@Validated
@PermissionModule(value = "图书")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    @GroupRequired
    @PermissionMeta(value = "查询单个图书")
    @Logger(template = "{user.nickname}查看一本图书")
    public BookDO getBook(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        BookDO book = bookService.getById(id);
        if (book == null) {
            throw new NotFoundException(10022);
        }
        return book;
    }

    @GetMapping("")
    @GroupRequired
    @PermissionMeta(value = "查询图书列表")
    @Logger(template = "{user.nickname}查看了图书列表")
    public List<BookDO> getBooks() {
        List<BookDO> books = bookService.findAll();
        return books;
    }


    @GetMapping("/search")
    @GroupRequired
    @PermissionMeta(value = "搜索图书")
    @Logger(template = "{user.nickname}搜索了图书")
    public List<BookDO> searchBook(@RequestParam(value = "q", required = false, defaultValue = "") String q) {
        List<BookDO> books = bookService.getBookByKeyword("%" + q + "%");
        return books;
    }


    @PostMapping("")
    @GroupRequired
    @PermissionMeta(value = "创建图书")
    @Logger(template = "{user.nickname}创建了一本图书")
    public CreatedVO createBook(@RequestBody @Validated CreateOrUpdateBookDTO validator) {
        bookService.createBook(validator);
        return new CreatedVO(12);
    }


    @PutMapping("/{id}")
    @GroupRequired
    @PermissionMeta(value = "更新图书")
    @Logger(template = "{user.nickname}更新了一本图书")
    public UpdatedVO updateBook(@PathVariable("id") @Positive(message = "{id.positive}") Integer id, @RequestBody @Validated CreateOrUpdateBookDTO validator) {
        BookDO book = bookService.getById(id);
        if (book == null) {
            throw new NotFoundException(10022);
        }
        bookService.updateBook(book, validator);
        return new UpdatedVO(13);
    }


    @DeleteMapping("/{id}")
    @GroupRequired
    @PermissionMeta(value = "删除图书", module = "图书")
    @Logger(template = "{user.nickname}删除了一本图书")
    public DeletedVO deleteBook(@PathVariable("id") @Positive(message = "{id.positive}") Integer id) {
        BookDO book = bookService.getById(id);
        if (book == null) {
            throw new NotFoundException(10022);
        }
        bookService.deleteById(book.getId());
        return new DeletedVO(14);
    }


}
