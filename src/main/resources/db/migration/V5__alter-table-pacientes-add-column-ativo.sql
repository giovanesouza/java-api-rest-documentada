alter table pacientes add ativo tinyint; -- altera tabela (add nova coluna "ativo")
update pacientes set ativo = 1; -- atualiza tabela (registros antigos) para registros ativos = true (1)