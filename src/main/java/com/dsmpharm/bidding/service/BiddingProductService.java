package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingProductMapper;
import com.dsmpharm.bidding.pojo.BiddingProduct;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.PageResult;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * Created by Grant on 2020/07/23
 */
@Service
public class BiddingProductService {
    private static Logger log = LoggerFactory.getLogger(BiddingProductService.class);

    @Resource
    private BiddingProductMapper biddingProductMapper;

    @Resource
    private IdWorker idWorker;

    /**
     * 添加产品 提交
     * @param map
     * @return
     */
    public Result insertSub(Map map) {
        String name =map.get("name").toString();
        String code =map.get("code").toString();
        String commonName =map.get("commonName").toString();
        String standards =map.get("standards").toString();
        String enName =map.get("enName").toString();
        String dosageForm =map.get("dosageForm").toString();
        BiddingProduct biddingProduct=new BiddingProduct();
        biddingProduct.setCode(code);
        biddingProduct.setName(name);
        biddingProduct.setCommonName(commonName);
        biddingProduct.setStandards(standards);
        biddingProduct.setEnName(enName);
        biddingProduct.setDosageForm(dosageForm);
        try {
            biddingProduct.setDelflag("0");
            int i = biddingProductMapper.selectCount(biddingProduct);
            if (i > 0) {
                return new Result<>(false, StatusCode.ERROR, "该产品已存在，请勿重复添加");
            }
            biddingProduct.setId(idWorker.nextId() + "");
            biddingProduct.setStatus("0");
            int insert = biddingProductMapper.insert(biddingProduct);
            if (insert > 0) {
                return new Result<>(true, StatusCode.OK, "添加成功");
            }
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "添加失败");
    }

    public List<BiddingProduct> selectAll() {
        return biddingProductMapper.selectAll();
    }

    /**
     * 根据ID查询产品信息
     *
     * @param id
     * @return
     */
    public Result findById(String id) {
        try {
            BiddingProduct biddingProduct = biddingProductMapper.selectByPrimaryKey(id);
            return new Result<>(true, StatusCode.OK, "查询成功", biddingProduct);
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    /**
     * 根据ID更新产品信息
     * @param map
     * @return
     */
    public Result updateById(Map map) {
        String id =map.get("id").toString();
        String name =map.get("name").toString();
        String code =map.get("code").toString();
        String commonName =map.get("commonName").toString();
        String standards =map.get("standards").toString();
        String enName =map.get("enName").toString();
        String dosageForm =map.get("dosageForm").toString();
        BiddingProduct biddingProduct=new BiddingProduct();
        biddingProduct.setId(id);
        biddingProduct.setCode(code);
        biddingProduct.setName(name);
        biddingProduct.setCommonName(commonName);
        biddingProduct.setStandards(standards);
        biddingProduct.setEnName(enName);
        biddingProduct.setDosageForm(dosageForm);
        try {
            BiddingProduct biddingProduct1 = biddingProductMapper.selectByPrimaryKey(biddingProduct.getId());
            if (biddingProduct1 == null || (biddingProduct1.getDelflag().equals("1"))) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            biddingProduct.setStatus("0");
            int i1 = biddingProductMapper.selectCount(biddingProduct);
            if (i1 > 0) {
                return new Result<>(false, StatusCode.ERROR, "该条记录已存在");
            }
            int i = biddingProductMapper.updateByPrimaryKeySelective(biddingProduct);
            if (i > 0) {
                return new Result<>(true, StatusCode.OK, "修改成功");
            }
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "修改失败");
    }

    /**
     * 根据ID删除产品信息
     *
     * @param id
     * @return
     */
    public Result delete(String id) {
        try {
            BiddingProduct biddingProduct = biddingProductMapper.selectByPrimaryKey(id);
            if (biddingProduct == null || (biddingProduct.getDelflag().equals("1"))) {
                return new Result<>(false, StatusCode.ERROR, "该条记录不存在");
            }
            biddingProduct.setDelflag("1");
            int i = biddingProductMapper.updateByPrimaryKeySelective(biddingProduct);
            if (i > 0) {
                return new Result<>(true, StatusCode.OK, "删除成功");
            }
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "删除失败");
    }

    public List<BiddingProduct> list(BiddingProduct biddingProduct) {
        return biddingProductMapper.select(biddingProduct);
    }

    public Result list(Map map) {
        try {
            Integer currentPage = Integer.valueOf(map.get("currentPage").toString());
            Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
            String name = map.get("name").toString();
            List<BiddingProduct> productList = biddingProductMapper.selectAllByNoDel(name);
            PageHelper.startPage(currentPage, pageSize);
            List<BiddingProduct> products = biddingProductMapper.selectAllByNoDel(name);
            PageInfo pageInfo = new PageInfo<>(products);
            pageInfo.setTotal(productList.size());
            PageResult pageResult = new PageResult(pageInfo.getTotal(), products);
            return new Result<>(true, StatusCode.OK, "查询成功",pageResult);
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }

    public Integer selectCount(BiddingProduct biddingProduct) {
        return biddingProductMapper.selectCount(biddingProduct);
    }

    public int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

    /**
     * 添加产品 保存
     * @param map
     * @return
     */
    public Result insertPro(Map map) {
        String name =map.get("name").toString();
        String code =map.get("code").toString();
        String commonName =map.get("commonName").toString();
        String standards =map.get("standards").toString();
        String enName =map.get("enName").toString();
        String dosageForm =map.get("dosageForm").toString();
        BiddingProduct biddingProduct=new BiddingProduct();
        biddingProduct.setCode(code);
        biddingProduct.setName(name);
        biddingProduct.setCommonName(commonName);
        biddingProduct.setStandards(standards);
        biddingProduct.setEnName(enName);
        biddingProduct.setDosageForm(dosageForm);
        try {
            biddingProduct.setDelflag("0");
            int i = biddingProductMapper.selectCount(biddingProduct);
            if (i > 0) {
                return new Result<>(false, StatusCode.ERROR, "该产品已存在，请勿重复添加");
            }
            biddingProduct.setId(idWorker.nextId() + "");
            biddingProduct.setStatus("1");
            int insert = biddingProductMapper.insert(biddingProduct);
            if (insert > 0) {
                return new Result<>(true, StatusCode.OK, "保存成功");
            }
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
        return new Result<>(false, StatusCode.ERROR, "保存失败");
    }

    /**
     * @param ids
     * @return
     */
    public Result deleteIds(String ids) {
        try {
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                String id = split[i];
                BiddingProduct biddingProduct = biddingProductMapper.selectByPrimaryKey(id);
                biddingProduct.setDelflag("1");
                biddingProductMapper.updateByPrimaryKeySelective(biddingProduct);
            }
            return new Result<>(true, StatusCode.OK, "删除成功");
        } catch (Exception e) {
            log.error(e.toString(),e);
            return new Result<>(false, StatusCode.ERROR, "呀! 服务器开小差了~");
        }
    }
}