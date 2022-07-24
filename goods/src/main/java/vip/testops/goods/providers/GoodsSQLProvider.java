package vip.testops.goods.providers;

import vip.testops.goods.entities.criteria.GoodsCriteria;

public class GoodsSQLProvider {
    public String selectByCriteria(GoodsCriteria criteria) {
        StringBuilder stringBuilder = new StringBuilder("select * from t_goods where 1=1");
        if(criteria.getName() != null){
            stringBuilder.append(" and name like \"%\"#{name}\"%\"");
        }
        if(criteria.getPriceMin() != null) {
            stringBuilder.append(" and price >= #{priceMin}");
        }
        if(criteria.getPriceMax() != null) {
            stringBuilder.append(" and price <= #{priceMax}");
        }
        if(criteria.getPageSize() != null && criteria.getPageSize() > 0) {
            int current = criteria.getCurrent() != null && criteria.getCurrent() > 0 ? criteria.getCurrent() : 1;
            int start = criteria.getPageSize() * (current - 1);
            stringBuilder.append(" limit ")
                    .append(start)
                    .append(", ")
                    .append(criteria.getPageSize());
        }
        return stringBuilder.toString();
    }

    public String selectCountByCriteria(GoodsCriteria criteria) {
        StringBuilder stringBuilder = new StringBuilder("select count(*) from t_goods where 1=1");
        if(criteria.getName() != null){
            stringBuilder.append(" and name like \"%\"#{name}\"%\"");
        }
        if(criteria.getPriceMin() != null) {
            stringBuilder.append(" and price >= #{priceMin}");
        }
        if(criteria.getPriceMax() != null) {
            stringBuilder.append(" and price <= #{priceMax}");
        }
        return stringBuilder.toString();
    }
}
